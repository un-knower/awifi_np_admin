package com.awifi.np.admin.interceptor;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月3日 上午9:18:28
 * 创建作者：沈叶峰
 * 文件名称：AuthInterceptorTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminUser;

@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml","classpath:spring/ApplicationContext-mvc.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
public class AuthInterceptorTest {

	    @Autowired
	    private WebApplicationContext wac;
	    private MockMvc mockMvc;

	    private MockHttpServletRequest request;    
	    @Before
	    public void setUp() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	    }
	    
	    /**
	     * 有权限
	     * @throws Exception
	     * @author 沈叶峰 
	     * @date 2017年2月4日 上午8:39:16
	     */
	    @Test
	    public void AuthTest()  throws Exception {
	    	NPAdminUser adminUser=new NPAdminUser();
	    	
	        String requestBody  = JSONObject.toJSONString(null);
	        String result =  mockMvc.perform(get("/admin/permissions")
	        		.sessionAttr(Constants.ADMIN_SESSION_KEY, adminUser)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestBody)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
	                .andReturn().getResponse().getContentAsString();
	        
	        JSONObject resultObj = JSONObject.parseObject(result);

//	        assertEquals(resultObj.getString("code"),"0");
	        

	    }
	    @Test
	    public void AuthTest1()  throws Exception {
	    	NPAdminUser adminUser=new NPAdminUser();
	    	adminUser.setRoleId(1001);
	        String requestBody  = JSONObject.toJSONString(null);
	        String result =  mockMvc.perform(get("/admin/permissions")
	        		.sessionAttr(Constants.ADMIN_SESSION_KEY, adminUser)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestBody)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
	                .andReturn().getResponse().getContentAsString();
	        
	        JSONObject resultObj = JSONObject.parseObject(result);

//	        assertEquals(resultObj.getString("code"),"0");
	        

	    }
	    @Test
	    public void AuthTest2()  throws Exception {
	    	NPAdminUser adminUser=new NPAdminUser();
	    	adminUser.setRoleId(1001);
	        String requestBody  = JSONObject.toJSONString(null);
	        String result =  mockMvc.perform(get("/admin/platform")
	        		.sessionAttr(Constants.ADMIN_SESSION_KEY, adminUser)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestBody)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
	                .andReturn().getResponse().getContentAsString();
	        
	        JSONObject resultObj = JSONObject.parseObject(result);

//	        assertEquals(resultObj.getString("code"),"0");
	        

	    }
	    /**
	     * 无权限
	     * @throws Exception
	     * @author 沈叶峰 
	     * @date 2017年2月4日 上午8:39:34
	     */
	    @Test
	    public void NoAuthTest()  throws Exception {
	        String requestBody  = JSONObject.toJSONString(null);
	        String result =  mockMvc.perform(post("/admin/permission")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(requestBody)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
	                .andReturn().getResponse().getContentAsString();
	        


	    }
}
