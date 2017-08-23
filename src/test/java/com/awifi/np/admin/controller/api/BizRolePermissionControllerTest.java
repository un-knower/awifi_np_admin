/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年5月10日 上午9:06:21
* 创建作者：王冬冬
* 文件名称：BizRolePermissionControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
//指定Bean的配置文件信息
//@ContextHierarchy({
//      @ContextConfiguration(name = "parent", locations = "classpath:spring/ApplicationContext.xml"),
//      @ContextConfiguration(name = "child", locations = "classpath:spring/ApplicationContext-mvc.xml")
//})
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BizRolePermissionControllerTest extends BaseController{
    
    private MockMvc mockMvc;

    @Mock
    private BizRoleServiceImpl bizRoleService;
    @Mock
    private ServiceServiceImpl serviceService;
    @Mock
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;
    
    
    //使mock对象的使用类可以注入mock对象
    @InjectMocks
    private BizRolePermissionController bizRolePermissionController;

    @Before
    public void setUp() {
        // 将打上Mockito标签的对象起作用，使得Mock的类被Mock，使用了Mock对象的类自动与Mock对象关联
        MockitoAnnotations.initMocks(this);
        // mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(bizRolePermissionController).build();

    }

    @Test
    public void testqueryBizRoleIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("E1000008")).andReturn();
    }

    @Test
    public void testqueryServiceIsNull() throws Exception {
        NPService npService = new NPService();
        NPBizRole npBizRole = new NPBizRole();
        npBizRole.setId(1L);
        when(bizRoleService.getById(1L)).thenReturn(npBizRole);
        when(serviceService.getById(1)).thenReturn(npService);
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("E1040000")).andReturn();
                                                                                         
    }
  
    @Test
    public void testqueryRolePermissionApiIsNull() throws Exception {
        NPService npService = new NPService();
        NPBizRole npBizRole = new NPBizRole();
        List<NPService> list=new ArrayList<NPService>();
        list.add(npService);
        npBizRole.setId(1L);
        when(bizRoleService.getById(1L)).thenReturn(npBizRole);
        when(serviceService.getByRoleId(1L)).thenReturn(list);
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("E1020001")).andReturn();
                                                                                         
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testqueryException() throws Exception {
        NPService npService = new NPService();
        NPBizRole npBizRole = new NPBizRole();
        List<NPService> list=new ArrayList<NPService>();
        npService.setRolePermissionApi("xxxxx");
        list.add(npService);
        npBizRole.setId(1L);
        when(bizRoleService.getById(1L)).thenThrow(Exception.class);
        when(serviceService.getByRoleId(1L)).thenReturn(list);
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("E1000004")).andReturn();
                                                                                         
    }
    
    @Test
    public void testqueryException1() throws Exception {
        NPService npService = new NPService();
        NPBizRole npBizRole = new NPBizRole();
        List<NPService> list=new ArrayList<NPService>();
        npService.setRolePermissionApi("xxxxx");
        list.add(npService);
        npBizRole.setId(1L);
        when(bizRoleService.getById(1L)).thenReturn(npBizRole);
        when(serviceService.getByRoleId(1L)).thenReturn(list);
        JSONObject o=  new JSONObject();
        o.put("code", "0");
        when(bizRoleInterfaceService.getRoleInterfaceRelation(1L, list)).thenThrow(Exception.class);
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("E1020002")).andReturn();
                                                                                         
                                                                                         
    }
    
    @Test
    public void testSuccess() throws Exception {
        NPService npService = new NPService();
        NPBizRole npBizRole = new NPBizRole();
        List<NPService> list=new ArrayList<NPService>();
        npService.setRolePermissionApi("xxxxx");
        list.add(npService);
        npBizRole.setId(1L);
        when(bizRoleService.getById(1L)).thenReturn(npBizRole);
        when(serviceService.getByRoleId(1L)).thenReturn(list);
        JSONObject o=  new JSONObject();
        o.put("code", "0");
        when(bizRoleInterfaceService.getRoleInterfaceRelation(1L, list)).thenReturn(o);
        MvcResult result = mockMvc.perform(get("/externalapi/query/interface?access_token=access_token&servicecode=xxx&roleId=1")
                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andDo(print()).andExpect(jsonPath("$.code").value("0")).andReturn();
                                                                                         
    }
}
