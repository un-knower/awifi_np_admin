package com.awifi.np.admin.controller.permission;

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
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.IAdminPermissionService;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月8日 下午4:49:37
* 创建作者：王冬冬
* 文件名称：PermissionControllerTest.java
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
public class PermissionControllerTest {
	
	private String urlprefix = "/admin/";
    private MockMvc mockMvc;

    //如果该对象需要mock，则加上此Annotate
    @Mock
	private IAdminPermissionService permissionService;

    //使mock对象的使用类可以注入mock对象
    @InjectMocks
    private PermissionController permissionController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
    }
	
    @Test
    public void getAllPermissionsTest() throws Exception{
    	List<NPAdminPermission> userList=new ArrayList<NPAdminPermission>();
//    	NPPage page=new NPPage();
//    	page.setPage(12);
        when(permissionService.getPermissionListAll()).thenReturn(userList);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(get(urlprefix+"/allpermissions")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    }
    
    @Test
    public void listPermissionsTest() throws Exception{
    	List<NPAdminPermission> userList=new ArrayList<NPAdminPermission>();
    	NPPage page=new NPPage();
    	page.setPage(12);
        when(permissionService.getPermissionList(page)).thenReturn(userList);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(get(urlprefix+"/permissions")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    }

    @Test
    public void addPermissionsTest() throws Exception{
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
        when(permissionService.addPermisson(permission)).thenReturn(1);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/permission")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(permission))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    @Test
    public void addPermissionsTest1() throws Exception{
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
        when(permissionService.addPermisson(permission)).thenThrow(Exception.class);

        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(post(urlprefix+"/permission")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(permission))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("E1003004"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    
    @Test
    public void updatePermissionTest() throws Exception{
    	
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
        when(permissionService.updatePermisson(permission)).thenReturn(1);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(put(urlprefix+"/permission")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(permission))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    	
    }
    
    @Test
    public void updatePermissionTest2() throws Exception{
        NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
        when(permissionService.updatePermisson(permission)).thenThrow(Exception.class);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(put(urlprefix+"/permission")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(JSONObject.toJSONString(permission))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("E1003005"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    
    @Test
    public void getPermissionByidTest() throws Exception{
    	Integer id=2;
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
    	permission.setId(2);
        when(permissionService.getPermissonById(id)).thenReturn(permission);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(get(urlprefix+"/permission/"+id)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    @Test
    public void deletePermissionTest() throws Exception{
    	String ids="100,200";
    	
        when(permissionService.deletePermisson(ids.split(","))).thenReturn(1);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(delete(urlprefix+"/permission")
        		.param("permissionids", ids)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    @Test
    public void deletePermissionTest1() throws Exception{
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(delete(urlprefix+"/permission")
        		.param("permissionids", "")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("E1003006"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    @Test
    public void deletePermissionTest2() throws Exception{
        String ids="100,200";
    	
        when(permissionService.deletePermisson(ids.split(","))).thenThrow(Exception.class);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(delete(urlprefix+"/permission")
        		.param("permissionids", ids)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("E1003003"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    
    
    
    @Test
    public void getPermissionByRoleidTest() throws Exception{
    	Integer id=2;
    	List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
    	permission.setId(2);
    	list.add(permission);
        when(permissionService.getPermissionListByRoleId(id)).thenReturn(list);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(get(urlprefix+"/permissions/"+id)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
    @Test
    public void getPermissionByRoleidTest1() throws Exception{
        
    	Integer id=2;
    	List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
    	NPAdminPermission permission=new NPAdminPermission();
    	permission.setPermissionName("name");
    	permission.setId(2);
    	list.add(permission);
        when(permissionService.getPermissionListByRoleId(id)).thenThrow(Exception.class);
        //得到MvcResult自己验证
        MvcResult result = mockMvc.perform(get(urlprefix+"/permissions/"+id)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(jsonPath("$.code").value("E1003001"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/
    }
}
