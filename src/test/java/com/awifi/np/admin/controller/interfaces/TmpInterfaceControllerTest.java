package com.awifi.np.admin.controller.interfaces;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.tolls4test.BizUtils;
import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.controller.api.InterfaceRegisterControllerTest;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPService;
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
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/8
 * 创建作者：卢朱娜
 * 文件名称：TmpInterfaceControllerTest.java
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
public class TmpInterfaceControllerTest {
    private String urlprefix = TestDATA.serviceUrl+"/";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    public static JSONArray listTmpInterface(MockMvc mockMvc, Integer sid) throws Exception {
        String result = mockMvc.perform(get(TestDATA.serviceUrl+"/" + sid +"/tmpinterface")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);
        assertEquals(resultObj.getString("code"),"0");

        JSONArray jsonArray = resultObj.getJSONArray("data");
        return jsonArray;
    }

    /**
     * 1 模拟注册几个临时接口
     * 2 获取列表
     * 3 批量更新notCheckList 不为空 notRegisterList为空
     * 4 获取列表
     */
    @Test
    public void tmpInterfaceTest() throws Exception {
        //模拟注册几个临时接口
        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
        JSONArray serviceJsonArray =
                BizUtils.registerInterface(mockMvc, interfaceRegisterDTO, TestDATA.getService());

        NPService npService = serviceJsonArray.getObject(0, NPService.class);
        //获取列表
        String result = mockMvc.perform(get(urlprefix+npService.getId()+"/tmpinterface")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);
        assertEquals(resultObj.getString("code"),"0");

        JSONArray jsonArray = resultObj.getJSONArray("data");
        assertSame(2, jsonArray.size());
        NPInterfaceTmp tmpInterface_1 = jsonArray.getObject(0, NPInterfaceTmp.class);
        NPInterfaceTmp tmpInterface_2 = jsonArray.getObject(1, NPInterfaceTmp.class);


        //批量更新notCheckList 不为空 notRegisterList为空
        HashMap<String, Object> paramMap = new HashMap<>();
        ArrayList<Integer> notRegisterList = new ArrayList<>();
        paramMap.put("notRegisterList", notRegisterList);
        ArrayList<Integer> notCheckList = new ArrayList<>();
        notCheckList.add(tmpInterface_1.getId());
        notCheckList.add(tmpInterface_2.getId());
        paramMap.put("notCheckList", notCheckList);
        paramMap.put("sid", npService.getId());
        String requestBody = JSONObject.toJSONString(paramMap);
        mockMvc.perform(put(urlprefix+npService.getId()+"/tmpinterface")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn();

        //获取列表
        jsonArray = listTmpInterface(mockMvc, npService.getId());
        assertSame(2, jsonArray.size());
        tmpInterface_1 = jsonArray.getObject(0, NPInterfaceTmp.class);
        tmpInterface_2 = jsonArray.getObject(1, NPInterfaceTmp.class);
        assertEquals(false, tmpInterface_1.getIfcheck());
        assertEquals(false, tmpInterface_2.getIfcheck());
        assertEquals(new Byte((byte)1), tmpInterface_1.getStatus());
        assertEquals(new Byte((byte)1), tmpInterface_2.getStatus());

    }




}
