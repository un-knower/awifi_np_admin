package com.awifi.np.admin.controller.template;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IServiceService;
import com.awifi.np.admin.service.ITemplateService;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年2月9日 上午9:19:29
 * 创建作者：沈叶峰
 * 文件名称：TemplateControllerTest.java
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
public class TemplateControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private static String templatesUrl = "/admin/templates";
    private static String templateUrl = "/admin/template";
    
    @InjectMocks
    private TemplateController templateController;
    @Mock
    private ITemplateService templateService;
    
    @Mock
    private IServiceService serviceService;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
    }
   
   
  @Test
  public void testListTemplate() throws UnsupportedEncodingException, Exception{

      String result = mockMvc.perform(get(templatesUrl).param("page", "1").param("pageSize","3").param("keyword","template1")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
            .andReturn().getResponse().getContentAsString();
    JSONObject resultObj = JSONObject.parseObject(result);
    assertEquals(resultObj.getString("code"),"0");
    when(templateService.listPageTemplates(anyObject())).thenThrow(BaseException.class);
    result = mockMvc.perform(get(templatesUrl).param("page", "1").param("pageSize","3").param("keyword","template1")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
            .andReturn().getResponse().getContentAsString();
    resultObj = JSONObject.parseObject(result);
    assertEquals(resultObj.getString("code"),"E1000004");
  }
  
  
  @Test
  public void testAddTemplate() throws UnsupportedEncodingException, Exception{
      List<NPService> list = new ArrayList<NPService>();
      NPService service=new NPService();
      list.add(service);
      NPTemplate template = new NPTemplate();
      template.setId(10000);
      template.setTemplateName("template1");
      template.setTemplateCode("login");
      template.setCreateDate(new Date());
      template.setUpdateDate(new Date());
      template.setServiceCode("devsvr");
      template.setSuitCode("suit1"); 
      when(serviceService.getByServiceCode(template.getServiceCode())).thenReturn(list);
      
      mockMvc.perform(post(templateUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
              .andReturn();
      
      NPAdminUser adminUser=new NPAdminUser();
      mockMvc.perform(post(templateUrl)
              .sessionAttr(Constants.ADMIN_SESSION_KEY,adminUser)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
              .andReturn();
      
      when(serviceService.getByServiceCode(template.getServiceCode())).thenReturn(null);
      mockMvc.perform(post(templateUrl)
              .sessionAttr(Constants.ADMIN_SESSION_KEY,adminUser)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeException))
              .andReturn();
      
      when(templateService.listPageTemplates(anyObject())).thenThrow(BaseException.class);
      mockMvc.perform(post(templateUrl)
              .sessionAttr(Constants.ADMIN_SESSION_KEY,adminUser)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeException))
              .andReturn();
  }
  
  @Test
  public void testUpdateTemplate() throws UnsupportedEncodingException, Exception{
      List<NPService> list = new ArrayList<NPService>();
      NPService service=new NPService();
      list.add(service);
      NPTemplate template = new NPTemplate();
      template.setId(10000);
      template.setTemplateName("template1");
      template.setTemplateCode("login");
      template.setCreateDate(new Date());
      template.setUpdateDate(new Date());
      template.setServiceCode("devsvr");
      template.setSuitCode("suit1"); 
      when(serviceService.getByServiceCode(template.getServiceCode())).thenReturn(list);
      
      mockMvc.perform(put(templateUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
              .andReturn();
      when(serviceService.getByServiceCode(template.getServiceCode())).thenThrow(BaseException.class);
      mockMvc.perform(put(templateUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(JSONObject.toJSONString(template))
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeException))
              .andReturn();
      
//      when(serviceService.getByServiceCode(template.getServiceCode())).thenThrow(Exception.class);
//      mockMvc.perform(put(templateUrl)
//              .contentType(MediaType.APPLICATION_JSON)
//              .content(JSONObject.toJSONString(template))
//              .accept(MediaType.APPLICATION_JSON)) //执行请求
//              .andDo(print())
//              .andExpect(jsonPath("$.code").value(Constants.ECodeException))
//              .andReturn();
  }
  
  @Test
  public void testDeleteTemplate() throws UnsupportedEncodingException, Exception{
      mockMvc.perform(delete(templateUrl+"/-1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value("E1000001"))
              .andReturn();
      
      
      mockMvc.perform(delete(templateUrl+"/1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
              .andReturn();
      
      Mockito.doThrow(Exception.class).when(templateService).deleteTemplate(1);
      mockMvc.perform(delete(templateUrl+"/1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value("E1000004"))
              .andReturn();
      
      
  }
  
  @Test
  public void testGetTemplateById() throws UnsupportedEncodingException, Exception{
      mockMvc.perform(get(templateUrl+"/-1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value("E1000001"))
              .andReturn();
      
      NPTemplate template = new NPTemplate();
      template.setId(10000);
      template.setTemplateName("template1");
      template.setTemplateCode("login");
      template.setCreateDate(new Date());
      template.setUpdateDate(new Date());
      template.setServiceCode("devsvr");
      template.setSuitCode("suit1"); 
      when(templateService.getTemplate(10000)).thenReturn(template);
     String result=mockMvc.perform(get(templateUrl+"/1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
              .andReturn().getResponse().getContentAsString();
     
      Mockito.doThrow(Exception.class).when(templateService).deleteTemplate(1);
      mockMvc.perform(delete(templateUrl+"/1")
              .accept(MediaType.APPLICATION_JSON)) //执行请求
              .andDo(print())
              .andExpect(jsonPath("$.code").value("E1000004"))
              .andReturn();
      
      
  }
   @Test
   public void templateTest () throws Exception { 
//   	        //添加一条记录
//	   NPTemplate template = new NPTemplate();
//	        template.setId(10000);
//
//	        template.setTemplateName("template1");
//	        template.setTemplateCode("login");
//	        template.setCreateDate(new Date());
//	        template.setUpdateDate(new Date());
//	        template.setServiceCode("devsvr");
//	        template.setSuitCode("suit1"); 
//	        addTemplateTest(template);
//           //添加一条记录
//	        NPTemplate template1 = new NPTemplate();
//	        template1.setId(10001);
//	        template1.setTemplateName("template2");
//	        template1.setTemplateCode("login");
//	        template1.setCreateDate(new Date());
//	        template1.setUpdateDate(new Date());
//	        template1.setServiceCode("merchantsvr");
//	        template1.setSuitCode("suit2");
////	        template1.setContent("2222");
//	       addTemplateTest(template1);
//	       
//	       
//	       
//	       
//	          String result = mockMvc.perform(get(templatesUrl).param("page", "1").param("pageSize","3").param("keyword","template1")
//	                    .accept(MediaType.APPLICATION_JSON))
//	                    .andDo(print())
//	                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                    .andReturn().getResponse().getContentAsString();
//	            JSONObject resultObj = JSONObject.parseObject(result);
//	            assertEquals(resultObj.getString("code"),"0");
//	            
//	            JSONObject data = resultObj.getJSONObject("data");
//	            JSONArray pageList = data.getJSONArray("templateList");
//	            NPTemplate templatenew = pageList.getObject(0, NPTemplate.class);
////	            templatenew.setContent("aabbcc");
//	            updateTemplateTest(templatenew);
//	            
//	            
//	            
//	            //测试获取一个模板详情
//	           result =   mockMvc.perform(get(templateUrl+"/"+templatenew.getId())
//	                    .contentType(MediaType.APPLICATION_JSON)
//	                    .accept(MediaType.APPLICATION_JSON)) //执行请求
//	                    .andDo(print())
//	                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//	                    .andReturn().getResponse().getContentAsString();
//	          
//	           resultObj = JSONObject.parseObject(result);
//	           JSONObject newSuit =   resultObj.getJSONObject("data");
//	      
////	           assertEquals(newSuit.getString("content"),"aabbcc");
//	           
//	           //删除一条套码
//	           mockMvc.perform(delete(templateUrl+"/"+templatenew.getId())
//	                   .contentType(MediaType.APPLICATION_JSON)
//	                   .accept(MediaType.APPLICATION_JSON)) //执行请求
//	                   .andDo(print())
//	                   .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//	                   .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//	                   .andReturn().getResponse().getContentAsString();
//	         

   } 

}
