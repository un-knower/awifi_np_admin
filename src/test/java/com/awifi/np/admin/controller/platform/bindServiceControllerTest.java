package com.awifi.np.admin.controller.platform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.tolls4test.BizUtils;
import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformService;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.ArrayList;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/19
 * 创建作者：卢朱娜
 * 文件名称：bindServiceControllerTestOld.java
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
public class bindServiceControllerTest {
    private String urlprefix = "/admin/platform";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void bindServiceTest() throws Exception{

        //添加一个platform，并查询出来
        NPPlatform npPlatform = TestDATA.getNPPlatform();
        npPlatform = BizUtils.insertPlatform(mockMvc, npPlatform);

        //添加platform2
        NPPlatform npPlatform_2 = TestDATA.getNPPlatform("lzn000");
        npPlatform_2 = BizUtils.insertPlatform(mockMvc, npPlatform_2);



        //添加几个服务
        ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
        for(int i=0;i<serviceArrayList.size();i++){
            NPService item = serviceArrayList.get(i);
            if(i==0){
                item.setPid(npPlatform.getId());
            }else{
                item.setPid(npPlatform_2.getId());
            }
            String requestBody = JSONObject.toJSONString(item);
            mockMvc.perform(post("/admin/service")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
                    .accept(MediaType.APPLICATION_JSON)) //执行请求
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                    .andReturn();
        }

        /**获取平台可选服务列表和已选服务列表*/
        String result = mockMvc.perform(get(urlprefix+ "/"+npPlatform.getId()+ "/service")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);
        JSONObject data = resultObj.getJSONObject("data");
        JSONArray result_bindedList = data.getJSONArray("bindedList");

        //验证bindedList中只有一个服务，而且该服务归属于该平台
        assertEquals(result_bindedList.size(), 1);
        NPPlatformService result_ps = result_bindedList.getObject(0, NPPlatformService.class);
        assertTrue(result_ps.getIsOwner());

        /**绑定服务列表中的前三个服务*/
        ArrayList<String> serviceCodeList = new ArrayList<>();
        for(int i=0;i<3;i++){
            serviceCodeList.add(serviceArrayList.get(i).getServiceCode());
        }
        String requestBody = JSONObject.toJSONString(serviceCodeList);
        mockMvc.perform(post(urlprefix+ "/"+npPlatform.getId()+ "/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));

        /**获取平台可选服务列表和已选服务列表*/
        result = mockMvc.perform(get(urlprefix+ "/"+npPlatform.getId()+ "/service")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn().getResponse().getContentAsString();

        resultObj = JSONObject.parseObject(result);
        data = resultObj.getJSONObject("data");
        result_bindedList = data.getJSONArray("bindedList");
        assertEquals(serviceCodeList.size(), result_bindedList.size());
        NPPlatformService ps0 = result_bindedList.getObject(0, NPPlatformService.class);
        NPPlatformService ps1 = result_bindedList.getObject(1, NPPlatformService.class);
        NPPlatformService ps2 = result_bindedList.getObject(2, NPPlatformService.class);
        assertTrue(ps0.getIsOwner());
        assertEquals(serviceCodeList.get(0), ps0.getServiceCode());
        assertFalse(ps1.getIsOwner());
        assertEquals(serviceCodeList.get(1), ps1.getServiceCode());
        assertFalse(ps2.getIsOwner());
        assertEquals(serviceCodeList.get(2), ps2.getServiceCode());
        assertEquals(0, ps0.getListOrder().intValue());
        assertEquals(1, ps1.getListOrder().intValue());
        assertEquals(2, ps2.getListOrder().intValue());


        /**绑定0个服务*/
        serviceCodeList.clear();
        requestBody = JSONObject.toJSONString(serviceCodeList);
        mockMvc.perform(post(urlprefix+ "/"+npPlatform.getId()+ "/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();

        /**获取平台可选服务列表和已选服务列表*/
        result = mockMvc.perform(get(urlprefix+ "/"+npPlatform.getId()+ "/service")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn().getResponse().getContentAsString();

        resultObj = JSONObject.parseObject(result);
        data = resultObj.getJSONObject("data");
        result_bindedList = data.getJSONArray("bindedList");
        assertEquals(0, result_bindedList.size());

    }

}
