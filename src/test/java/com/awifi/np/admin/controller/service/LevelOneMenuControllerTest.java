package com.awifi.np.admin.controller.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.tolls4test.BizUtils;
import com.awifi.np.admin.tolls4test.TestDATA;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/4
 * 创建作者：卢朱娜
 * 文件名称：LevelOneMenuControllerTest.java
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
public class LevelOneMenuControllerTest {
    private String urlprefix = "/admin/levelonemenu";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    /**
     * 1、模拟2个服务
     * 2、更新为不是菜单
     * 3、余下几个更新为是菜单
     * 4、获取列表，isMenu，keword不为空
     * 4、获取列表，isMenu，keword为空
     * 5、获取详情
     */
    @Test
    public void levelOneMenuTest() throws Exception {
        //添加俩服务
        JSONArray jsonArray = BizUtils.insertService(mockMvc, TestDATA.getServiceList(),2);

        //倒序
        NPService service_1 = jsonArray.getObject(0,NPService.class);
        NPService service_0 = jsonArray.getObject(1,NPService.class);

        //更新为不是菜单
        service_0.setIsMenu(false);
        String requestBody = JSONObject.toJSONString(service_0);
        mockMvc.perform(put(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();

        //更新为是菜单
        service_1.setIsMenu(true);
        service_1.setHasSubmenu(false);
        service_1.setMenuName("MenuName");
        service_1.setMenuUrl("http://url");
        service_1.setTargetId("targetId");
        service_1.setRemark("remark");
        service_1.setMenuTreeApi("/dev/menutree");
        service_1.setRoleMenuApi("/dev/rolemenu");
        requestBody = JSONObject.toJSONString(service_1);
        mockMvc.perform(put(urlprefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();



        //获取列表，keyword,isMenu都不为空
        String result = mockMvc.perform(get(urlprefix)
                .param("page", "1")
                .param("pageSize","10")
                .param("isMenu",String.valueOf(true))
                .param("keyword","aaaaa")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        JSONObject data = resultObj.getJSONObject("data");
        assertSame(data.getIntValue("totalRecord"), 0);
        JSONArray pageList = data.getJSONArray("list");
        assertSame(pageList.size(), 0);

        //获取列表，keyword,isMenu都为空
        result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");
//
//        data = resultObj.getJSONObject("data");
//        assertSame(data.getIntValue("totalRecord"), 2);
//        pageList = (JSONArray) data.getJSONArray("list");
//        assertEquals(pageList.size(), 2);


        //获取详情
        mockMvc.perform(get(urlprefix+"/"+service_1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));


    }



}
