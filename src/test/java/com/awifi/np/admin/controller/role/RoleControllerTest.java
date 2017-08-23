package com.awifi.np.admin.controller.role;

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
import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.service.IAdminRoleService;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月8日 下午4:49:55
* 创建作者：王冬冬
* 文件名称：RoleControllerTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RoleControllerTest {

	    private String urlprefix = "/admin";
	    private MockMvc mockMvc;

	    //如果该对象需要mock，则加上此Annotate
	    @Mock
		private IAdminRoleService roleService;

	    //使mock对象的使用类可以注入mock对象
	    @InjectMocks
	    private RoleController roleController;

	    @Before
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
	    }
	    
	    @Test
	    public void listTest() throws Exception{
	    	
//	    	List<NPAdminRole> roleList=new ArrayList<NPAdminRole>();
//	    	NPPage page=new NPPage();
//	    	page.setPage(12);
//	        when(roleService.listRoles(page)).thenReturn(roleList);

	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(get(urlprefix+"/roles?page=1")
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    	
	    }
	    
	    @Test
	    public void addRoleTest() throws Exception{
	    	
	    	NPAdminRole role=new NPAdminRole();
	    	role.setId(11);
	    	
	        when(roleService.addRole(role)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(post(urlprefix+"/role")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(JSONObject.toJSONString(role))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
	    
	    @Test
	    public void updateRoleTest() throws Exception{
	    	
	    	NPAdminRole role=new NPAdminRole();
	    	role.setId(11);
	        when(roleService.updateRole(role)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(put(urlprefix+"/role")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(JSONObject.toJSONString(role))
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    	
	    }
	    
	    @Test
	    public void getRoleByidTest() throws Exception{
	    	Integer id=2;
	    	NPAdminRole role=new NPAdminRole();
	    	role.setId(11);
	        when(roleService.getRoleById(id)).thenReturn(role);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(get(urlprefix+"/role/"+id)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
	    
	    
	    @Test
	    public void deleteRoleTest() throws Exception{
	    	String ids="1,2";
	        when(roleService.deteleByIds(ids)).thenReturn(1);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(delete(urlprefix+"/role")
	        		.param("ids", ids)
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
	    
	    @Test
	    public void allRolesTest() throws Exception{
	    	List<NPAdminRole> roleList=new ArrayList<NPAdminRole>();
	    	NPAdminRole role=new NPAdminRole();
	    	role.setRoleName("dd");
	    	roleList.add(role);
	        when(roleService.allRoleList()).thenReturn(roleList);
	        //得到MvcResult自己验证
	        MvcResult result = mockMvc.perform(get(urlprefix+"/allroles")
	                .accept(MediaType.APPLICATION_JSON)) //执行请求
	                .andDo(print())
	                .andExpect(jsonPath("$.code").value("0"))
	                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
	    }
}
