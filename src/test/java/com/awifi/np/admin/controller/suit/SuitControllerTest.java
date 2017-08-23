package com.awifi.np.admin.controller.suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPSuit;

/**
 * 皮肤测试类
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月23日 下午4:53:36
 * 创建作者：沈叶峰
 * 文件名称：SuitServiceControllerTest.java
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
public class SuitControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private static String suitsUrl = "/admin/suits";
    private static String suitUrl = "/admin/suit";



    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    public void addSuitTest(Integer id,String appId,String suitCode) throws Exception { 
        NPSuit suit = new NPSuit();
        suit.setId(id);
        suit.setAppId(appId);
        suit.setSuitCode(suitCode);
        suit.setSuitName(suitCode);
    	mockMvc.perform(post(suitUrl)
                 .contentType(MediaType.APPLICATION_JSON) 
	        	  .content(JSONObject.toJSONString(suit))
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                 .andReturn();
       
        return ;
    }
    
    
    public void updateSuitTest(NPSuit suit) throws Exception { 
        mockMvc.perform(put(suitUrl)
                .contentType(MediaType.APPLICATION_JSON)
	        	.content(JSONObject.toJSONString(suit))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();
      
       return ;
   }
 
    
    @Test
    public void suitTest() throws Exception { 
    	
    	   //添加一条记录
            addSuitTest(200,"112233","skin1");
            //添加一条记录
            addSuitTest(300,"22233344","skin2");
            
            String    result = mockMvc.perform(get("/admin/allsuits")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andReturn().getResponse().getContentAsString();
            JSONObject  resultObj = JSONObject.parseObject(result);
            assertEquals(resultObj.getString("code"),"0");
            
             result = mockMvc.perform(get(suitsUrl).param("page", "1").param("pageSize","3").param("keyword","skin1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andReturn().getResponse().getContentAsString();
             resultObj = JSONObject.parseObject(result);

            assertEquals(resultObj.getString("code"),"0");
            

            

            //更新一条记录
            JSONObject data = resultObj.getJSONObject("data");
//            assertSame(data.getIntValue("totalRecord"), 2);
            JSONArray pageList = data.getJSONArray("suitList");
            NPSuit suit = pageList.getObject(0, NPSuit.class);
            suit.setAppId("aabbcc");
            updateSuitTest(suit);
            
            
            //测试获取一个套码详情
           result =   mockMvc.perform(get(suitUrl+"/"+suit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)) //执行请求
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                    .andReturn().getResponse().getContentAsString();
          
           resultObj = JSONObject.parseObject(result);
           JSONObject newSuit =   resultObj.getJSONObject("data");
      
           assertEquals(newSuit.getString("appId"),"aabbcc");
           
           //删除一条套码
           mockMvc.perform(delete(suitUrl+"/"+suit.getId())
                   .contentType(MediaType.APPLICATION_JSON)
                   .accept(MediaType.APPLICATION_JSON)) //执行请求
                   .andDo(print())
                   .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                   .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                   .andReturn().getResponse().getContentAsString();
         
  
    }
}
