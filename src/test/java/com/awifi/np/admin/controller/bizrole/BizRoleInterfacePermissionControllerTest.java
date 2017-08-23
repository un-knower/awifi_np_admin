package com.awifi.np.admin.controller.bizrole;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.IAdminRoleService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.tolls4test.TestDATA;

@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BizRoleInterfacePermissionControllerTest {
	
	
	private String urlprefix ="/admin/bizrole";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Mock
    private BizRoleServiceImpl bizRoleService;
    @Mock
    private ServiceServiceImpl serviceService;
    @Mock
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;
    
    @InjectMocks
    BizRoleInterfacePermissionController controller;
    @Before
    public void setUp() {
    	 MockitoAnnotations.initMocks(this);
	     this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    
    @Test
    public void test1() throws Exception{
    	 NPService npService = new NPService();
         NPBizRole npBizRole = new NPBizRole();
         npBizRole.setId(1L);
    	when(bizRoleService.getById(1L)).thenReturn(npBizRole);
    	when(serviceService.getById(1)).thenReturn(npService);
    	 MvcResult result = mockMvc.perform(get(urlprefix+"/1/service/1/interface")
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("E1020001"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    	
    }

    
    /**
     * RolePermissionApi()不为空
     * @throws Exception
     */
    @Test
    public void test2() throws Exception{
    	 NPService npService = new NPService();
    	 npService.setRolePermissionApi("role permision");
         NPBizRole npBizRole = new NPBizRole();
         npBizRole.setId(1L);
    	when(bizRoleService.getById(1L)).thenReturn(npBizRole);
    	when(serviceService.getById(1)).thenReturn(npService);
    	JSONObject json=new JSONObject();
    	json.put("code", "0");
    	when(bizRoleInterfaceService.getRoleInterfaceRelation(1L, npService)).thenReturn(json);
    	 MvcResult result = mockMvc.perform(get(urlprefix+"/1/service/1/interface")
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    	
    }
    
    
    
    @Test
    public void test3() throws Exception{
    	 NPService npService = new NPService();
    	 npService.setRolePermissionApi("role permision");
         NPBizRole npBizRole = new NPBizRole();
         npBizRole.setId(1L);
    	when(bizRoleService.getById(1L)).thenReturn(npBizRole);
    	when(serviceService.getById(1)).thenReturn(npService);
    	JSONObject json=new JSONObject();
    	json.put("code", "0");
    	
    	List<String> interfaceCodeList=new ArrayList<String>();
    	when(bizRoleInterfaceService.bindRoleInterface(1L, npService, interfaceCodeList)).thenReturn(json);
    	 MvcResult result = mockMvc.perform(put(urlprefix+"/1/service/1/interface")
    			    .contentType(MediaType.APPLICATION_JSON)
    			    .content(JSONObject.toJSONString(interfaceCodeList))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    	
    }
    
    
    @Test
    public void test4() throws Exception{
    	 NPService npService = new NPService();
    	 npService.setRolePermissionApi("role permision");
         NPBizRole npBizRole = new NPBizRole();
         npBizRole.setId(1L);
    	when(bizRoleService.getById(1L)).thenReturn(npBizRole);
    	when(serviceService.getById(1)).thenReturn(npService);
    	JSONObject json=new JSONObject();
    	json.put("code", "0");
    	
    	List<String> interfaceCodeList=new ArrayList<String>();
    	when(bizRoleInterfaceService.bindRoleInterface(1L, npService, interfaceCodeList)).thenThrow(Exception.class);
    	 MvcResult result = mockMvc.perform(put(urlprefix+"/1/service/1/interface")
    			    .contentType(MediaType.APPLICATION_JSON)
    			    .content(JSONObject.toJSONString(interfaceCodeList))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("E1020002"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    	
    }
}
