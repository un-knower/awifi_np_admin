package com.awifi.np.admin.controller.bizdata;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.IInterfaceService;
import com.awifi.np.admin.service.IServiceService;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.NPAdminUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月8日 下午4:48:25
* 创建作者：王冬冬
* 文件名称：DataAccessControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpClientUtil.class)
@SuppressStaticInitializationFor("com.awifi.np.admin.utils.HttpClientUtil")//阻止静态代码块运行 
public class DataAccessControllerTest extends BaseController{

	    @Autowired
	    private WebApplicationContext wac;
	    private MockMvc mockMvc;
	    
	    @Mock
		private IInterfaceService interfaceService;
	    
	   
		
//		@Autowired
//		private IServiceService iServiceService;

		@Mock
		private IServiceService iServiceService;
		
		@InjectMocks
		private DataAccessController dataController;
	    @Before
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	        PowerMockito.mockStatic(HttpClientUtil.class);
	    }
	
//	   @Test
//	   public void testNoParam() throws Exception{
//		   
//		   MvcResult result = mockMvc.perform(get("/externalapi/interface/data?servicecode=xxxxx")
//	                .accept(MediaType.APPLICATION_JSON)) //执行请求
//	                .andDo(print())
//	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                .andExpect(jsonPath("$.code").value("E1009002"))
//	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
//		   
//	   }
	   
	
	/**
	 * 错误的serviceCode
	 * @throws Exception
	 */
	@Test
	   public void testInterfaceNotRegister() throws Exception{
		   
//		   MvcResult result = mockMvc.perform(get("/externalapi/interface/data?servicecode=xxxxx&interfacecode=xxx")
//	                .accept(MediaType.APPLICATION_JSON)) //执行请求
//	                .andDo(print())
//	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                .andExpect(jsonPath("$.code").value("E1009002"))
//	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
		   
		Map result=dataController.dataAccess(null, null, null, "xxxx", "xxx", null);
		Assert.assertEquals("E1009002", (String)result.get("code"));
	   }
	
	@Test
    public void testServiceNotRegister() throws Exception{
        
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("XXXX");
        npinterface.setInterfaceCode("localhot");
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
        when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
        List<NPService> serviceList=new ArrayList<NPService>();
        when(iServiceService.getByServiceCode(npinterface.getServiceCode())).thenReturn(serviceList);
 
        Map result=dataController.dataAccess(null, null, null, "XXXX", "localhot", null);
        Assert.assertEquals("E1015001", (String)result.get("code"));
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

    }
	
	@Test
    public void testPublishStatusIsNotOnline() throws Exception{
        
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("XXXX");
        npinterface.setInterfaceCode("localhot");
        NPService service=new NPService();
        service.setServiceCode("XXXX");
        service.setPublishStatus((byte)0);
        npinterface.setService(service);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
        when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
        List<NPService> serviceList=new ArrayList<NPService>();
        when(iServiceService.getByServiceCode(npinterface.getServiceCode())).thenReturn(serviceList);
        
        Map result=dataController.dataAccess(null, null, null, "XXXX", "localhot", null);
        Assert.assertEquals("E0000006", (String)result.get("code")); 
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

    }
	
	@Test
    public void testPublishStatusIsNotOnline2() throws Exception{
        
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("XXXX");
        npinterface.setInterfaceCode("localhot");
        NPService service=new NPService();
        service.setServiceCode("XXXX");
        service.setPublishStatus((byte)2);
        npinterface.setService(service);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
        when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
        List<NPService> serviceList=new ArrayList<NPService>();
        when(iServiceService.getByServiceCode(npinterface.getServiceCode())).thenReturn(serviceList);
          
        Map result=dataController.dataAccess(null, null, null, "XXXX", "localhot", null);
        Assert.assertEquals("E0000006", (String)result.get("code")); 
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

    }
	
	@Test
    public void testPublishStatusIsNotOnline3() throws Exception{
        
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("XXXX");
        npinterface.setInterfaceCode("localhot");
        NPService service=new NPService();
        service.setServiceCode("XXXX");
        service.setPublishStatus((byte)3);
        npinterface.setService(service);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
        when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
        List<NPService> serviceList=new ArrayList<NPService>();
        when(iServiceService.getByServiceCode(npinterface.getServiceCode())).thenReturn(serviceList);

        Map result=dataController.dataAccess(null, null, null, "XXXX", "localhot", null);
        Assert.assertEquals("E0000006", (String)result.get("code")); 
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

    }
	
	@Test
	public void testException() throws Exception{
	    
		NPInterface npinterface=new NPInterface();
		npinterface.setServiceCode("XXXX");
		npinterface.setInterfaceCode("localhot");
        NPService service=new NPService();
        service.setServiceCode("XXXX");
        
        npinterface.setService(service);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
		when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
		List<NPService> serviceList=new ArrayList<NPService>();

		npinterface.setIfcheck(false);
//		MvcResult result = mockMvc.perform(get("/externalapi/interface/data?servicecode=XXXX&interfacecode=localhot")
//	                .accept(MediaType.APPLICATION_JSON)) //执行请求
//	                .andDo(print())
//	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                .andExpect(jsonPath("$.code").value("E1009001"))
//	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
		
		 Map result=dataController.dataAccess(null, null, null, "XXXX", "localhot", null);
	     Assert.assertEquals("E1009001", (String)result.get("code")); 
	     JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

	}
	
	@Test
	public void testIfCheckisFalse() throws Exception{
		NPInterface npinterface=new NPInterface();
		npinterface.setServiceCode("XXXX");
		npinterface.setInterfaceCode("localhot");
		NPService service=new NPService();
        service.setPublishStatus((byte)1);
		npinterface.setService(service);
		npinterface.setIfcheck(false);
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
		when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);

		Map map=new HashMap();
		Map user=new HashMap();
		user.put("name", "dd");
		map.put("userInfo", user);
		map.put("servicecode","XXXX");
		JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));

		Map result=dataController.dataAccess(null, null, "access_token", "XXXX", "localhot", null);
        Assert.assertEquals("0", (String)result.get("code")); 
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

	}
	
	@Test
	public void testIfCheckIsTrueAndSafeInterface() throws Exception{
		NPInterface npinterface=new NPInterface();
		npinterface.setServiceCode("XXXX");
	    npinterface.setInterfaceCode("localhot");
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

		NPService service=new NPService();
		service.setPublishStatus((byte)1);
		service.setServiceHost("http://192.168.41.xx");
		service.setServicePort("80");
		service.setCheckAuth("/pubsrv/permission/check");
		service.setCheckSafe("/pubsrv/security/check");
		npinterface.setIfcheck(true);
		npinterface.setService(service);
		when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);
		
		Map map=new HashMap();
		Map user=new HashMap();
		user.put("name", "dd");
		map.put("userInfo", user);
		map.put("servicecode","XXXX");
		JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));
		
		Map result=dataController.dataAccess(null, null, "access_token", "XXXX", "localhot", null);
        Assert.assertEquals("0", (String)result.get("code")); 
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
	}
	
	
	@Test
    public void testIfCheckIsTrueAndAuthInterface() throws Exception{
	    
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("XXXX");
        npinterface.setInterfaceCode("localhot");
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());

        NPService service=new NPService();
        service.setPublishStatus((byte)1);
        service.setServiceHost("http://192.168.41.77");
        service.setServicePort("80");
        service.setCheckAuth("/pubsrv/permission/check");
        service.setCheckSafe("/pubsrv/security/check");
        npinterface.setIfcheck(true);
        npinterface.setService(service);
        
        when(interfaceService.getInterfaceByParam("XXXX","localhot")).thenReturn(npinterface);

        Map map=new HashMap();
        Map user=new HashMap();
        user.put("name", "dd");
        map.put("userInfo", user);
        map.put("servicecode","XXXX");
        JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));
//        MvcResult result = mockMvc.perform(get("/externalapi/interface/data?servicecode=XXXX&interfacecode=localhot&access_token=access_token")
//                    .accept(MediaType.APPLICATION_JSON)) //执行请求
//                    .andDo(print())
//                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                    .andExpect(jsonPath("$.code").value("E0000002"))
//                    .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
        
        Map result=dataController.dataAccess(null, null, "access_token", "XXXX", "localhot", null);
        Assert.assertEquals("0", (String)result.get("code"));
        JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npinterface.getServiceCode()).append("_").append(npinterface.getInterfaceCode()).toString());
    }
}
