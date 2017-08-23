package com.awifi.np.admin.controller.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.awifi.np.admin.controller.TestLoginDATA;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月24日 下午4:01:40
* 创建作者：王冬冬
* 文件名称：LoginControllerTest.java
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
public class LoginControllerTest {
  private String urlprefix = TestLoginDATA.loginUrl;

  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }


  /**
   * 1、测试参数为空
   */
  @Test
  public void platformTest1() throws Exception{
	  //1、测试参数为空
      mockMvc.perform(post(urlprefix)
              .contentType(MediaType.TEXT_PLAIN)
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value("E1001003"))
              .andReturn();
      
  }
	/**
	  * 2、测试session中没有验证码
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年2月3日 下午2:21:35
	 */
	@Test
	  public void platformTest2() throws Exception{
		  String loginAccount="admin";
		  String loginPwd="admin";
		  String vertifyCode="ddd";
	//      String requestBody = JSONObject.toJSONString(npPlatform);
	      mockMvc.perform(post(urlprefix)
	    		  .param("loginAccount", loginAccount)
	              .param("loginPwd",loginPwd)  
	              .param("vertifyCode",vertifyCode)
	              .contentType(MediaType.TEXT_PLAIN)
	              .accept(MediaType.APPLICATION_JSON))
	              .andDo(print())
	              .andExpect(jsonPath("$.code").value("E1001003"))
	              .andReturn();
	      
	  }
	
	
	/**
	  * 3、测试验证码
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年2月3日 下午2:21:35
	 */
	@Test
	  public void platformTest3() throws Exception{
		  String loginAccount="admin";
		  String loginPwd="admin";
		  String defaultCode="123456";
		  String vertifyCode="ddd";
	//      String requestBody = JSONObject.toJSONString(npPlatform);
	      mockMvc.perform(post(urlprefix)
	    		  .sessionAttr("vertifyCode", defaultCode)
	    		  .param("loginAccount", loginAccount)
	              .param("loginPwd",loginPwd)  
	              .param("vertifyCode",vertifyCode)
	              .contentType(MediaType.TEXT_PLAIN)
	              .accept(MediaType.APPLICATION_JSON))
	              .andDo(print())
	              .andExpect(jsonPath("$.code").value("E1001002"))
	              .andReturn();
	      
	  }
	
	
	/**
	  * 4、测试用户名密码错误
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年2月3日 下午2:21:35
	 */
	@Test
	  public void platformTest4() throws Exception{
		  String loginAccount="admin";
		  String loginPwd="admin1";
		  String defaultCode="123456";
	//      String requestBody = JSONObject.toJSONString(npPlatform);
	      mockMvc.perform(post(urlprefix)
	    		  .sessionAttr("vertifyCode", defaultCode)
	    		  .param("loginAccount", loginAccount)
	              .param("loginPwd",loginPwd)  
	              .param("vertifyCode",defaultCode)
	              .contentType(MediaType.TEXT_PLAIN)
	              .accept(MediaType.APPLICATION_JSON))
	              .andDo(print())
	              .andExpect(jsonPath("$.code").value("E1001004"))
	              .andReturn();
	      
	  }
	
	
	/**
	  * 测试验证码
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年2月3日 下午2:21:35
	 */
	@Test
	  public void Test3() throws Exception{
		  
	//      String requestBody = JSONObject.toJSONString(npPlatform);
	      mockMvc.perform(get("/public/verifyCode")
	              .contentType(MediaType.TEXT_PLAIN)
	              .accept(MediaType.TEXT_PLAIN))
	      		  .andDo(print())
	      		  .andReturn();
	      
	  }
}
