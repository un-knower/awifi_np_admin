package com.awifi.np.admin.controller.interfaces;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
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
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.tolls4test.TestDATA;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/3
 * 创建作者：卢朱娜
 * 文件名称：InterfaceControllerTest.java
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
public class InterfaceControllerTest {

    private String urlprefix = TestDATA.interfaceUrl;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @Mock
    InterfaceServiceImpl interfaceService;
    @Mock
    private ServiceServiceImpl serviceService;
    
    @InjectMocks
    InterfaceController interfaceController;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(interfaceController).build();
    }



    /**
     * 1、添加两个服务
     * 2、为两个服务添加相同的接口，连续5个
     * 3、添加的时候服务编码重复
     * 4、获取列表 serviceName不为空
     * 5、获取列表 keyword 和 serviceName 都不为空
     * 6、获取列表 keyword 和 serviceName 都为空
     * 7、获取某个接口的详情
     * 8、更新接口，服务不存在
     * 9、更新接口，编码重复
     * 10、更新接口，正常更新
     * 11、删除接口
     * 12、获取列表
     * @throws Exception
     */
//    @Test
//    public void platformTest() throws Exception {
//
//        //添加俩服务
//        JSONArray jsonArray = BizUtils.insertService(mockMvc, TestDATA.getServiceList(),2);
//
//        ArrayList<NPInterface> interfaceArrayList = TestDATA.getInterfaceList();
//
//        //倒序
////        NPService service_1 = jsonArray.getObject(0,NPService.class);
//        NPService service_0 = jsonArray.getObject(1,NPService.class);
//
//        //为两个服务添加相同的接口，连续5个
//        for(int i=0;i<2;i++){
//            NPService service = jsonArray.getObject(i, NPService.class);
//            for(NPInterface npInterface: interfaceArrayList){
//                npInterface.setServiceId(service.getId());
//                String requestBody = JSONObject.toJSONString(npInterface);
//                mockMvc.perform(post(urlprefix)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .accept(MediaType.APPLICATION_JSON)) //执行请求
//                        .andDo(print())
//                        .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                        .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                        .andReturn();
//            }
//        }
//
//        //添加的时候编码重复
//        NPInterface npInterface = interfaceArrayList.get(0);
//        npInterface.setServiceId(service_0.getId());
//        String requestBody = JSONObject.toJSONString(npInterface);
//        mockMvc.perform(post(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1013001"))
//                .andReturn();
//
//
//        //获取列表 serviceName不为空
//        String result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","10")
//                .param("serviceName", service_0.getServiceName())
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        JSONObject resultObj = JSONObject.parseObject(result);
//        assertEquals(resultObj.getString("code"),"0");
//
//        JSONObject data = resultObj.getJSONObject("data");
//        assertSame(data.getIntValue("totalRecord"), 5);
//
//
//        //获取列表 keyword 和 serviceName 都不为空
//        result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","10")
//                .param("keyword", "interfaceUrl0").param("serviceName", "service")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        resultObj = JSONObject.parseObject(result);
//        assertEquals(resultObj.getString("code"),"0");
//
//        data = resultObj.getJSONObject("data");
//        assertSame(data.getIntValue("totalRecord"), 2);
//
//
//        //获取列表 keyword 和 serviceName 都为空
//        result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","10").param("keyword", "interfaceName")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        resultObj = JSONObject.parseObject(result);
//
//        assertEquals(resultObj.getString("code"),"0");
//
//        data = resultObj.getJSONObject("data");
//        assertSame(data.getIntValue("totalRecord"), 10);
//        JSONArray pageList = (JSONArray) data.getJSONArray("list");
//        assertEquals(pageList.size(), 10);
//
//
//        //获取某个接口的详情
//        NPInterface npInterface_0 = pageList.getObject(0, NPInterface.class);
//        mockMvc.perform(get(urlprefix+"/"+npInterface_0.getId())
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));
//
//        mockMvc.perform(get(urlprefix+"/9999")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1000008"));
//
//        
//
//        //更新接口，服务不存在
//        npInterface_0.setServiceId(0);
//        requestBody = JSONObject.toJSONString(npInterface_0);
//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeBadParam))
//                .andReturn();
//
//
//        //更新接口，编码重复
//        NPInterface npInterface_1 = pageList.getObject(1, NPInterface.class);
//        npInterface_1.setInterfaceUrl(npInterface_0.getInterfaceUrl());
//        npInterface_1.setInterfaceMethod(npInterface_0.getInterfaceMethod());
//        npInterface_1.setServiceId(service_0.getId());
//        requestBody = JSONObject.toJSONString(npInterface_1);
//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1013001"))
//                .andReturn();
//
//        //更新接口
//        NPInterface npInterface_2 = pageList.getObject(2, NPInterface.class);
//        npInterface_2.setServiceId(service_0.getId());
//        requestBody = JSONObject.toJSONString(npInterface_2);
//        mockMvc.perform(put(urlprefix)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andReturn();
//
//
//        mockMvc.perform(delete(urlprefix+"/"+npInterface_0.getId())
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
//                .andReturn();
//
//        //获取列表 keyword 和 serviceName 都为空
//        result = mockMvc.perform(get(urlprefix).param("page", "1").param("pageSize","10").param("keyword", "interfaceName")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        resultObj = JSONObject.parseObject(result);
//
//        assertEquals(resultObj.getString("code"),"0");
//
//        data = resultObj.getJSONObject("data");
//        pageList = data.getJSONArray("list");
//        assertEquals(9, pageList.size());
//
//    }
    @Test
    public void addTest() throws Exception{
    	 NPInterface npInterface = new NPInterface();
//       npInterface.setServiceCode("111");
//       npInterface.setInterfaceCode("/users/{id}:GET");
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         npInterface.setRemark("remark");
//         npInterface.setServiceId("");
         String requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl(null);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("");
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("");
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(null);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET1");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000008"))
                 .andReturn();
    	
    }
    @Test
    public void addTest1() throws Exception{
    	 NPInterface npInterface = new NPInterface();
//       npInterface.setServiceCode("111");
//       npInterface.setInterfaceCode("/users/{id}:GET");
         npInterface.setInterfaceUrl("/interfaceUrlxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         npInterface.setRemark("remark");
    	 npInterface.setServiceId(1111);
         String requestBody = JSONObject.toJSONString(npInterface);
         
         NPService service=new NPService();
         when(serviceService.getById(1111)).thenReturn(null);
         mockMvc.perform(post(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000008"))
                 .andReturn();
         
    	 npInterface.setServiceId(1);
         requestBody = JSONObject.toJSONString(npInterface);
         NPAdminUser user=new NPAdminUser();
         when(serviceService.getById(1)).thenReturn(service);
         mockMvc.perform(post(urlprefix)
        		 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1013002"))
                 .andReturn();
         
         
         npInterface.setInterfaceUrl("/interfaceUrl");
         requestBody = JSONObject.toJSONString(npInterface);
         when(serviceService.getById(1)).thenReturn(service);
         when(interfaceService.insert(anyObject(), anyObject())).thenReturn("0");
         mockMvc.perform(post(urlprefix)
                 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("0"))
                 .andReturn();
         
         when(serviceService.getById(1)).thenReturn(service);
         when(interfaceService.insert(anyObject(), anyObject())).thenReturn("E1000001");
         mockMvc.perform(post(urlprefix)
                 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
    }
    
    @Test
    public void updateTest() throws Exception{
         NPInterface npInterface = new NPInterface();
         npInterface.setId(1);
//       npInterface.setServiceCode("111");
//       npInterface.setInterfaceCode("/users/{id}:GET");
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         npInterface.setRemark("remark");
//         npInterface.setServiceId("");
         String requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl(null);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("");
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("");
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(null);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
         
         
         npInterface.setServiceId(1111);
         npInterface.setInterfaceUrl("/interfaceUrl");
         npInterface.setInterfaceMethod("GET1");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         requestBody = JSONObject.toJSONString(npInterface);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000008"))
                 .andReturn();
        
    }
    @Test
    public void updateTest1() throws Exception{
         NPInterface npInterface = new NPInterface();
         npInterface.setId(1);
//       npInterface.setServiceCode("111");
//       npInterface.setInterfaceCode("/users/{id}:GET");
         npInterface.setInterfaceUrl("/interfaceUrlxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
         npInterface.setInterfaceMethod("GET");
         npInterface.setInterfaceName("interfaceName");
         npInterface.setIfcheck(false);
         npInterface.setRemark("remark");
         npInterface.setServiceId(1111);
         String requestBody = JSONObject.toJSONString(npInterface);
         
         NPService service=new NPService();
         when(serviceService.getById(1111)).thenReturn(null);
         mockMvc.perform(put(urlprefix)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000008"))
                 .andReturn();
         
         npInterface.setServiceId(1);
         requestBody = JSONObject.toJSONString(npInterface);
         NPAdminUser user=new NPAdminUser();
         when(serviceService.getById(1)).thenReturn(service);
         mockMvc.perform(put(urlprefix)
                 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1013002"))
                 .andReturn();
         
         
         npInterface.setInterfaceUrl("/interfaceUrl");
         requestBody = JSONObject.toJSONString(npInterface);
         when(serviceService.getById(1)).thenReturn(service);
         when(interfaceService.update(anyObject(), anyObject())).thenReturn("0");
         mockMvc.perform(put(urlprefix)
                 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("0"))
                 .andReturn();
         
         when(serviceService.getById(1)).thenReturn(service);
         when(interfaceService.update(anyObject(), anyObject())).thenReturn("E1000001");
         mockMvc.perform(put(urlprefix)
                 .sessionAttr(Constants.ADMIN_SESSION_KEY, user)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody)
                 .accept(MediaType.APPLICATION_JSON)) //执行请求
                 .andDo(print())
                 .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                 .andExpect(jsonPath("$.code").value("E1000001"))
                 .andReturn();
    }
    
    
    @Test
    public void testGetInterface() throws Exception{
        
        NPInterface npInterface = new NPInterface();
        npInterface.setId(1);
//      npInterface.setServiceCode("111");
//      npInterface.setInterfaceCode("/users/{id}:GET");
        npInterface.setInterfaceUrl("/interfaceUrlxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
        npInterface.setInterfaceMethod("GET");
        npInterface.setInterfaceName("interfaceName");
        npInterface.setIfcheck(false);
        npInterface.setRemark("remark");
        npInterface.setServiceId(1111);
        when(interfaceService.getByKey(11)).thenReturn(null);
        mockMvc.perform(get(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000008"))
                .andReturn();
        when(interfaceService.getByKey(11)).thenReturn(npInterface);
        mockMvc.perform(get(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn();
    }
    @Test
    public void testDeleteInterface() throws Exception{
        
        NPInterface npInterface = new NPInterface();
        npInterface.setId(1);
//      npInterface.setServiceCode("111");
//      npInterface.setInterfaceCode("/users/{id}:GET");
        npInterface.setInterfaceUrl("/interfaceUrlxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
        npInterface.setInterfaceMethod("GET");
        npInterface.setInterfaceName("interfaceName");
        npInterface.setIfcheck(false);
        npInterface.setRemark("remark");
        npInterface.setServiceId(1111);
        Mockito.doNothing().when(interfaceService).delete(11);
        mockMvc.perform(delete(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn();
        Mockito.doThrow(Exception.class).when(interfaceService).delete(11);
        mockMvc.perform(delete(urlprefix+"/11")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000004"))
                .andReturn();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testListPage() throws Exception{
        NPInterface npInterface = new NPInterface();
        npInterface.setId(1);
//      npInterface.setServiceCode("111");
//      npInterface.setInterfaceCode("/users/{id}:GET");
        npInterface.setInterfaceUrl("/interfaceUrlxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
        npInterface.setInterfaceMethod("GET");
        npInterface.setInterfaceName("interfaceName");
        npInterface.setIfcheck(false);
        npInterface.setRemark("remark");
        npInterface.setServiceId(1111);
        List<NPInterface> list=new ArrayList<NPInterface>();
        when(interfaceService.listPageInterface(anyObject(), anyString(), anyString())).thenReturn(list);
        mockMvc.perform(get(urlprefix+"?page=1&keyword=xxx&serviceName=xxx")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn();
        
        when(interfaceService.listPageInterface(anyObject(), anyString(), anyString())).thenThrow(Exception.class);
        mockMvc.perform(get(urlprefix+"?page=1&keyword=xxx&serviceName=xxx")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("E1000004"))
                .andReturn();
    }
}
