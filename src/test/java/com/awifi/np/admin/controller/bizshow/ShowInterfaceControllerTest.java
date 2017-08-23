package com.awifi.np.admin.controller.bizshow;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import com.awifi.np.admin.controller.bizdata.DataAccessController;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.service.IInterfaceService;
import com.awifi.np.admin.service.IServiceService;
import com.awifi.np.admin.service.ITemplateService;
import com.awifi.np.admin.utils.redis.JedisUtil;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月8日 下午4:49:18
* 创建作者：王冬冬
* 文件名称：ShowInterfaceControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
//指定Bean的配置文件信息
//@ContextHierarchy({
//      @ContextConfiguration(name = "parent", locations = "classpath:spring/ApplicationContext.xml"),
//      @ContextConfiguration(name = "child", locations = "classpath:spring/ApplicationContext-mvc.xml")
//})
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
        "classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ShowInterfaceControllerTest extends BaseController{
	
	  @Autowired
	    private WebApplicationContext wac;
	    private MockMvc mockMvc;
	    
	    @Mock
		private IInterfaceService interfaceService;
		
//		@Autowired
//		private IServiceService iServiceService;

	    @Mock
		private ITemplateService templateService;
	    
		@Mock
		private IServiceService iServiceService;
		
		@InjectMocks
		private ShowInterfaceController showInterfaceController;
	    @Before
	    public void setUp() {
//	        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	        MockitoAnnotations.initMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(showInterfaceController).build();
	    }
	    
	    /**
		 * 错误的serviceCode
		 * @throws Exception
		 */
		@Test
		   public void testServiceCodeIsNull() throws Exception{
//			/externalapi/interface/view?servicecode=xxxxx&suitcode=xxx&templatecode=xxx&access_token=access_token
			   MvcResult result = mockMvc.perform(get("/externalapi/interface/view?suitcode=xxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("E0000003"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
			   
		   }
		
		@Test
		public void testTemplatecodeIsNull() throws Exception{
			   MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&suitcode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("E0000007"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
			
		}
		
		@Test
		public void testSuitcodeNotNull() throws Exception{
		      JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
			  NPTemplate template=new NPTemplate(); 
			  when(templateService.selectByParam("xxxxx","xxx","xxx")).thenReturn(template);
			  
			   MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&suitcode=xxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("0"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
			 JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
		}
		
		@Test
		public void testSuitcodeNotNuLLAndTemplateNotRegister() throws Exception{
		    
	          JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
			  List<NPTemplate> templist=new ArrayList<NPTemplate>(); 
			  NPTemplate template=new NPTemplate();
			  template.setTemplateCode("xxx");
			  templist.add(template);
			  when(templateService.selectByParam("xxxxx","xxx","xxx")).thenReturn(null);
			  
			   MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&suitcode=xxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("E1008002"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	           JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
		}
		
		@Test
		public void testTokenHasUser() throws Exception{
            JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());

			 List<NPTemplate> templist=new ArrayList<NPTemplate>();
			  NPTemplate template=new NPTemplate();
			  template.setTemplateCode("xxx");
			  templist.add(template);
			  when(templateService.selectByParam("xxxxx","xxx","xxx")).thenReturn(template);

			Map map=new HashMap();
			Map user=new HashMap();
			user.put("name", "dd");
			map.put("userInfo", user);
			map.put("suitCode","xxx");
			JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));
			  MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("0"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	      JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());

		}
		
		
		@Test
		public void testGetSuitCode() throws Exception{
            JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());

			 List<NPTemplate> templist=new ArrayList<NPTemplate>(); 
			  NPTemplate template=new NPTemplate();
			  template.setTemplateCode("xxx");
			  templist.add(template);
			  when(templateService.selectByParam("xxxxx","xxx","xxx")).thenReturn(template);
			
			Map map=new HashMap();
			Map user=new HashMap();
			user.put("name", "dd");
			map.put("userInfo", user);
			JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));
			  MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("E0000005"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	       JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
		}
		
		@Test
		public void testGetSuitCode1() throws Exception{
            JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
			 List<NPTemplate> templist=new ArrayList<NPTemplate>();
			  NPTemplate template=new NPTemplate();
			  template.setTemplateCode("xxx");
			  templist.add(template);
			  when(templateService.selectByParam("xxxxx","xxx","xxx")).thenReturn(template);

			Map map=new HashMap();
			Map user=new HashMap();
			user.put("suitCode", "xxx");
			map.put("userInfo", user);
			JedisUtil.setex("access_token",Constants.TOKEN_EXPIRE_TIME,JSON.toJSONString(map));
			  MvcResult result = mockMvc.perform(get("/externalapi/interface/view?servicecode=xxxxx&templatecode=xxx&access_token=access_token")
		                .accept(MediaType.APPLICATION_JSON)) //执行请求
		                .andDo(print())
		                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		                .andExpect(jsonPath("$.code").value("0"))
		                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	       JedisUtil.del(new StringBuilder().append(Constants.TEMPLATE).append("xxxxx").append("-").append("xxx").append("-").append("xxx").toString());
		}

}
