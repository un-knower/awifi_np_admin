package com.awifi.np.admin.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.tolls4test.BizUtils;
import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dto.InterfaceDTO;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceTmpServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/17
 * 创建作者：卢朱娜
 * 文件名称：InterfaceRegisterControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
//XML风格
//表示使用Spring Test组件进行单元测试
@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
//指定Bean的配置文件信息
//@ContextHierarchy({
//        @ContextConfiguration(name = "parent", locations = "classpath:spring/ApplicationContext.xml"),
//        @ContextConfiguration(name = "child", locations = "classpath:spring/ApplicationContext-mvc.xml")
//})
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class InterfaceRegisterControllerTest extends BaseController{
    private String urlprefix = TestDATA.registerUrl;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Resource
    private ServiceServiceImpl serviceService;
    @Resource
    private InterfaceTmpServiceImpl interfaceTmpService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 测试interfaceMap为null
     */
    @Test
    public void badInterfaceMapTest() throws Exception {
        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
        interfaceRegisterDTO.setInterfaceMap(null);
        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);

        System.out.print(requestBody);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(ECodeParam))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/


    }

    /**
     * 测试interfaceMap内容为空
     */
    @Test
    public void emptyInterfaceMapTest() throws Exception {
        HashMap<String,InterfaceDTO> interfaceMap = new HashMap<String, InterfaceDTO>();
        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
        interfaceRegisterDTO.setInterfaceMap(interfaceMap);
        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(ECodeParam))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/


    }


    /**
     * 测试serviceCode非法
     */
    @Test
    public void badServiceCodeTest() throws Exception {
        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);


        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1015001"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/




    }



    /**
     * 临时表中没有已注册数据
     * 测试对于合法的参数 能否正确返回，而且成功添加到数据库
     */
    @Test
    public void successTest() throws Exception {
//        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
//
//        //模拟服务，注册接口
//        BizUtils.registerInterface(mockMvc, interfaceRegisterDTO, TestDATA.getService());
//
//        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);
//
//        System.out.print(requestBody);
//
//        //修改其中的一个配置，再重新请求一遍
//        NPInterfaceTmp npInterfaceTmp = TestDATA.getNPInterfaceTmp();
//        interfaceTmpService.updateByInterfaceCode(npInterfaceTmp);
//
//        //得到MvcResult自己验证
//        mockMvc.perform(post(urlprefix+"/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("0"))
//                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
//
//        List<NPInterfaceTmp> resultList = interfaceTmpService.listByServiceCode(TestDATA.serviceCode4test);
//        assertEquals(resultList.size(), interfaceRegisterDTO.getInterfaceMap().size());
//        for(NPInterfaceTmp item : resultList){
//            if(item.getInterfaceCode().equals(npInterfaceTmp.getInterfaceCode())){
//                assertEquals(item.getRemark(), npInterfaceTmp.getRemark());
//                assertEquals(item.getIfcheck(), npInterfaceTmp.getIfcheck());
//            }
//        }


    }


}
