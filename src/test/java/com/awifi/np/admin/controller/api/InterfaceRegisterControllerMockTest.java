package com.awifi.np.admin.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceTmpServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/17
 * 创建作者：卢朱娜
 * 文件名称：InterfaceRegisterControllerMockTest.java
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
public class InterfaceRegisterControllerMockTest extends BaseController{
    private String urlprefix = TestDATA.registerUrl;
    private MockMvc mockMvc;

    //如果该对象需要mock，则加上此Annotate
    @Mock
    private ServiceServiceImpl serviceService;
    @Mock
    private InterfaceTmpServiceImpl interfaceTmpService;

    //使mock对象的使用类可以注入mock对象
    @InjectMocks
    private InterfaceRegisterController registerController;


    @Before
    public void setUp() {
        //将打上Mockito标签的对象起作用，使得Mock的类被Mock，使用了Mock对象的类自动与Mock对象关联
        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();

    }


    /**
     * 测试之前没有已注册接口 && 前后maxDate两次值不同
     */
    @Test
    public void registerTest() throws Exception{
        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);

        //service被mock后，涉及到的方法调用，都需要模拟
        ArrayList<NPService> serviceList = TestDATA.getService();
        when(serviceService.getByServiceCode(TestDATA.serviceCode4test)).thenReturn(serviceList);

        ArrayList<NPInterfaceTmp> oldInterfaceList = new ArrayList<NPInterfaceTmp>();
        when(interfaceTmpService.listByServiceCode(TestDATA.serviceCode4test)).thenReturn(oldInterfaceList);

        //第一次调用返回null，第二次调用返回最新的Date
        doReturn(null).doReturn(new Date()).when(interfaceTmpService).getMaxDate(TestDATA.serviceCode4test);


        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1015002"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/

        Mockito.verify(serviceService).getByServiceCode(TestDATA.serviceCode4test);
        Mockito.verify(interfaceTmpService).listByServiceCode(TestDATA.serviceCode4test);
        Mockito.verify(interfaceTmpService, times(2)).getMaxDate(TestDATA.serviceCode4test);



    }





}
