package com.awifi.np.admin.controller.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.PlatformServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.tolls4test.TestDATA;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/22
 * 创建作者：卢朱娜
 * 文件名称：ServiceControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */

@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ServiceControllerTest {
    private String urlprefix = TestDATA.serviceUrl;

    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;

    private MockHttpServletRequest request;    
    
    @InjectMocks
    private ServiceController controller;

    @Mock
    private ServiceServiceImpl serviceService;
    @Mock
    private PlatformServiceImpl platformService;
    
    @Before
    public void setUp() {
    	 MockitoAnnotations.initMocks(this);
	     this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    /**
     * 0、添加一个假的平台，并获取平台id
     * 1、添加服务，测试平台id不存在
     * 2、连续添加两次服务，测试服务编码重复
     * 3、连续添加4个服务，其中一个状态为上线中，一个为已上线，
     *      pageSize=3, keyword=servicecode, 测试分页 和查询
     * 4、测试获取详情 id=0 记录不存在
     * 5、测试获取详情 id正常 正常返回
     * 6、测试update 服务状态不是未上线, 修改服务编码
     * 7、测试update 参数正常，update的结果正确
     **/
    @Test
    public void serviceTest() throws Exception {

//        NPPlatform npPlatform = TestDATA.getNPPlatform();
//        npPlatform = BizUtils.insertPlatform(mockMvc, npPlatform);
//
//        /** 1、添加服务，测试平台id不存在*/
//        NPService npService = TestDATA.getService().get(0);
//        npService.setPid(0);
//        String requestBody = JSONObject.toJSONString(npService);
//
//        mockMvc.perform(post(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeBadParam))
//                .andReturn();
//
//
//        /**2、连续添加两次服务，测试服务编码重复*/
//        npService.setPid(npPlatform.getId());
//        requestBody = JSONObject.toJSONString(npService);
//
//        mockMvc.perform(post(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andReturn();
//
//        mockMvc.perform(post(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1012001"))
//                .andReturn();
//
//
//        /** 3、连续添加5个服务，其中有状态为上线中和已上线，
//         * pageSize=3, keyword=servicecode, 测试分页 和查询*/
//        ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
//        for(NPService item : serviceArrayList){
//            item.setPid(npPlatform.getId());
//            requestBody = JSONObject.toJSONString(item);
//
//            mockMvc.perform(post(urlprefix)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(requestBody)
//                    .accept(MediaType.APPLICATION_JSON)) //执行请求
//                    .andDo(print())
//                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                    .andReturn();
//        }
//
//        String result = mockMvc.perform(get(urlprefix)
//                .param("page", "2")
//                .param("pageSize","3")
//                .param("keyword", TestDATA.serviceCodePre)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        JSONObject resultObj = JSONObject.parseObject(result);
//
//        assertEquals(resultObj.getString("code"),"0");
//
//        JSONObject data = resultObj.getJSONObject("data");
//        assertSame(serviceArrayList.size(), data.getIntValue("totalRecord"));
//        JSONArray pageList = data.getJSONArray("list");
//        assertEquals(3, pageList.size());
//
//        NPService npService_0 = pageList.getObject(0, NPService.class);
//        npService_0.setPid(npPlatform.getId());
//        NPService npService_1 = pageList.getObject(1, NPService.class);
//        npService_1.setPid(npPlatform.getId());
//
//
//
//
//        /**4、测试获取详情 id=0 记录不存在*/
//        mockMvc.perform(get(urlprefix+"/0")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeBadParam));
//
//        /** 5、测试获取详情 id正常 正常返回，且平台名称正确*/
//        mockMvc.perform(get(urlprefix + "/" + npService_0.getId())
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andExpect(jsonPath("$.data.platformName").value(npPlatform.getPlatformName()));//验证平台名称
//
//
//        /** 6、测试update 服务状态不是未上线, 修改服务编码*/
//        npService_1.setPublishStatus(Constants.SERVICR_PUBLISH_ING);
//        requestBody = JSONObject.toJSONString(npService_1);
//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andReturn();
//
//        npService_1.setServiceCode("ssssssssssssssssssss");
//        requestBody = JSONObject.toJSONString(npService_1);
//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1012002"))
//                .andReturn();
//
//
//        /** 7、测试update 参数正常，update的结果正确*/
//        requestBody = JSONObject.toJSONString(npService_0);

//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andReturn();

    }

    
    @Test
    public void test() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
//        mockMvc.perform(get(urlprefix)
//                .contentType(MediaType.TEXT_PLAIN)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("0"));
    	 mockMvc.perform(get(urlprefix)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(jsonPath("$.code").value("0"))
                 .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    @Test
    public void test1() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	
    	List<NPService> list = new ArrayList<NPService>();
        when(serviceService.listOnlineService(true)).thenReturn(list);
        mockMvc.perform(get(urlprefix)
        		.param("keyword", "keyword")
        		.param("isOnline", "true")
        		.param("isMenu", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"));
    	
    	
    }
    
    @Test
    public void test2() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	
    	List<NPService> list = new ArrayList<NPService>();
        when(serviceService.listPageService(page,"keyword")).thenReturn(list);
        mockMvc.perform(get(urlprefix)
        		.param("keyword", "keyword")
        		.param("isOnline", "false")
        		.param("isMenu", "true")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"));
    	
    	
    }
    
    //--------------
    @Test
    public void test3() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	
    	NPService service=new NPService();
        when(serviceService.getJoinInfo(11)).thenReturn(service);
        mockMvc.perform(get(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"));
    }
    @Test
    public void test4() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	
        when(serviceService.getJoinInfo(11)).thenReturn(null);
        mockMvc.perform(get(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000008"));
    }
    //--------------------
    @Test
    public void test5() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	NPService npService=new NPService();
//        when(serviceService.listPageService(page,"keyword")).thenReturn(list);
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
    	
    	
        npService.setServiceCode("xxxxxx");
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
        
        
        npService.setServiceKey("xxxxxx");
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
        
        
        npService.setPid(11);
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000008"));
        
        
        npService.setServiceCode("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1012003"));
        
        
        npService.setServiceCode("xxxxxxxxxx");
        NPPlatform npPlatform = new NPPlatform();
        when(platformService.getById(npService.getPid())).thenReturn(npPlatform);
        List<NPService> serviceList =new ArrayList<NPService>();
        when(serviceService.getByServiceCode(npService.getServiceCode())).thenReturn(serviceList);
        mockMvc.perform(post(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"));
        
    }
    
    @Test
    public void test6() throws Exception{
    	NPPage page =new NPPage();
    	page.setPageSize(-1);
    	NPService npService=new NPService();
//        when(serviceService.listPageService(page,"keyword")).thenReturn(list);
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
    	
        npService.setId(100);
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
        
        npService.setServiceCode("xxxxxx");
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
        
        
        npService.setServiceKey("xxxxxx");
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000001"));
        
        
        npService.setPid(11);
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000008"));
        
        
        npService.setServiceCode("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1012003"));
        
        
        npService.setServiceCode("xxxxxxxxxx");
        npService.setPublishStatus((byte)0);
        NPPlatform npPlatform = new NPPlatform();
        npPlatform.setId(11);
        when(platformService.getById(npService.getPid())).thenReturn(npPlatform);
//        List<NPService> serviceList =new ArrayList<NPService>();
        when(serviceService.getById(npService.getId())).thenReturn(npService);
        
//        when(serviceService.updateAndBindPlatform(npService, npPlatform)).thenReturn("0");
        mockMvc.perform(put(urlprefix)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(npService))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000004"));
        
    }
}
