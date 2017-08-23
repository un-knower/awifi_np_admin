package com.awifi.np.admin.controller.platform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.entity.NPPlatform;
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

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/19
 * 创建作者：卢朱娜
 * 文件名称：PlatformControllerTest.java
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
public class PlatformControllerTest {
    private String urlprefix = TestDATA.platformUrl;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    /**
     * 1、测试url不合法
     * 2、同时添加两遍 测试appid重复
     * 3、连续添加4个，pageSize=3, keyword=appid, 测试分页 和查询
     * 4、测试获取详情 id=0 记录不存在
     * 5、测试获取详情 id正常 正常返回
     * 6、测试update id和appid的组合在记录中不存在
     * 7、测试update 参数正常，update的结果正确
     */
    @Test
    public void platformTest() throws Exception{
        /**1、测试url不合法 */
        NPPlatform npPlatform = TestDATA.getNPPlatform();
        npPlatform.setPlatformUrl("aaaaaaaaaaaaa");
        String requestBody = JSONObject.toJSONString(npPlatform);

        mockMvc.perform(post(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1011002"))
                .andReturn();


        /** 2、同时添加两遍 测试appid重复*/
        npPlatform = TestDATA.getNPPlatform();
        requestBody = JSONObject.toJSONString(npPlatform);
        mockMvc.perform(post(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();
        mockMvc.perform(post(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1011001"))
                .andReturn();

     /** 3、连续添加4个，pageSize=3, keyword=appid, 测试分页 和查询*/
        ArrayList<NPPlatform> npPlatformArrayList = TestDATA.getNPPlatformList();
        for(NPPlatform item : npPlatformArrayList){
            String tmpRequestBody = JSONObject.toJSONString(item);
            mockMvc.perform(post(urlprefix)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(tmpRequestBody)
                    .accept(MediaType.APPLICATION_JSON)) //执行请求
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                    .andReturn();
        }

        String result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","3").param("keyword", TestDATA.appIdPre)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        JSONObject data = resultObj.getJSONObject("data");
        assertSame(data.getIntValue("totalRecord"), 4);
        JSONArray pageList = (JSONArray) data.getJSONArray("list");
        assertEquals(pageList.size(), 3);

        NPPlatform npPlatform_0 = pageList.getObject(0, NPPlatform.class);


        /**4、测试获取详情 id=0 记录不存在*/
        mockMvc.perform(get(urlprefix+"/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeBadParam));



        /** 5、测试获取详情 id正常 正常返回*/
        mockMvc.perform(get(urlprefix + "/" + npPlatform_0.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));



        /** 6、测试update appid已被占用*/
        npPlatform = TestDATA.getNPPlatform();
        npPlatform.setId(npPlatform_0.getId());
        requestBody = JSONObject.toJSONString(npPlatform);
        mockMvc.perform(put(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1011001"))
                .andReturn();


        /** 7、测试update 参数正常，update的结果正确*/
        npPlatform_0.setPlatformName("lzntest");
        requestBody = JSONObject.toJSONString(npPlatform_0);
        mockMvc.perform(put(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();


        /** 8、测试获取所有平台*/
        mockMvc.perform(get(urlprefix).param("pageSize","-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));

    }



}
