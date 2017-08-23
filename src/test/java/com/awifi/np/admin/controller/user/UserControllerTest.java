package com.awifi.np.admin.controller.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.IAdminUserService;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月8日 下午4:50:16
* 创建作者：王冬冬
* 文件名称：UserControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserControllerTest extends BaseController{
	  private String urlprefix = "/admin";
	    private MockMvc mockMvc;

	    //如果该对象需要mock，则加上此Annotate
	    @Mock
	    private IAdminUserService userService;

	    //使mock对象的使用类可以注入mock对象
	    @InjectMocks
	    private UserController userController;

	    @Before
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	    }
	    
	    @Test
	    public void listTest() throws Exception{
//	    	List<NPAdminUser> userList=new ArrayList<NPAdminUser>();
//	    	NPAdminUser user=new NPAdminUser();
//	    	user.setLoginAccount("admin");
//	    	user.setLoginPwd("admin");
//            userList.add(user);
//            
//	    	NPPage page=new NPPage();
//	    	page.setPage(12);
//	        when(userService.listUsers(page, null, null)).thenReturn(userList);

	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(get(urlprefix+"/users?page=1")
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    	
	    }
	    @Test
	    public void addUserTest() throws Exception{
	    	
	    	NPAdminUser user=new NPAdminUser();
	    	user.setLoginAccount("dd");
	    	user.setLoginPwd("admin");
	        when(userService.addUser(user)).thenReturn(1);

	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(post(urlprefix+"/user")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(JSONObject.toJSONString(user))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    	
	    }
	    
	    @Test
	    public void updateUserTest() throws Exception{
	    	
	    	NPAdminUser user=new NPAdminUser();
	    	user.setLoginAccount("dd");
	    	user.setLoginPwd("admin");
	        when(userService.updateUser(user)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(put(urlprefix+"/user")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(JSONObject.toJSONString(user))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    	
	    }
	    
	    @Test
	    public void getUserByidTest() throws Exception{
	    	Integer id=2;
	    	NPAdminUser user=new NPAdminUser();
	    	user.setId(2);
	    	user.setLoginAccount("dd");
	    	user.setLoginPwd("admin");
	        when(userService.getUserById(id)).thenReturn(user);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(get(urlprefix+"/user/"+id)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
	    
	    @Test
	    public void deleteTest() throws Exception{
	    	String[] ids=new String[]{"1","2"};
	    	
	        when(userService.deteleByIds(ids)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(delete(urlprefix+"/user")
	        		.param("ids", ids)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
	    
	    
	    @Test
	    public void updatePasswordTest() throws Exception{
	    	NPAdminUser user=new NPAdminUser();
	    	user.setLoginAccount("dd");
	    	user.setLoginPwd("admin");
	        when(userService.updatePassword(user)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(put(urlprefix+"/user/password")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(JSONObject.toJSONString(user))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
}
