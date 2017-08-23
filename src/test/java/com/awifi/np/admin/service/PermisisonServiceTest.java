package com.awifi.np.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPAdminPermissionMapper;
import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminRolePermissionMapper;
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.AdminPermissionServiceImpl;
import com.awifi.np.admin.utils.SerializableUtilOld;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月6日 下午4:04:40
* 创建作者：王冬冬
* 文件名称：PermisisonServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@RunWith(PowerMockRunner.class)
public class PermisisonServiceTest {
	
	 @Mock
     private NPAdminRolePermissionMapper adminRolePermissionMapper;
	 @Mock
     private NPAdminPermissionMapper adminPermissionMapper;
	 @Mock
     private NPAdminRoleMapper adminRoleMapper;
	 
	 @InjectMocks
	 private AdminPermissionServiceImpl adminPermissionService;
	 
	 @Before
	  public void setUp() {
		  
	  }
	  
	 @Test
	  public void testgetPermissionListAll() throws Exception {
		  
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
		  NPAdminPermission p= new NPAdminPermission();
		  p.setParentId(0);
		  p.setPermissionName("");
		  permissionList.add(p);
		  
		  PowerMockito.when(adminPermissionMapper.selectByExample(null)).thenReturn(permissionList);
		  List<NPAdminPermission> resultList=adminPermissionService.getPermissionListAll();
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  JedisUtil.del(Constants.PERMISSONS_KEY);
	  }
	 
	  @Test
	  public void testgetPermissionIdListByRoleId() throws Exception {
		  adminPermissionService.getPermissionIdListByRoleId(1001);
	  }
	 
	  @Test
	  public void testaddPermisson() throws Exception {
		  
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  NPAdminPermission permission=new NPAdminPermission();
		  permission.setPermissionName("all");
		  permission.setParentId(0);
		  permission.setId(1);
		  
		  NPAdminPermission permission2=new NPAdminPermission();
		  permission2.setPermissionName("haha");
		  permission2.setParentId(1);
		  permission2.setId(2);
		  List<NPAdminPermission> permissionList1=new ArrayList<NPAdminPermission>();
		  permissionList1.add(permission2);
		  
		  permission.setChildPermission(permissionList1);
		  PowerMockito.when(adminPermissionMapper.insertSelective(permission)).thenReturn(1);
		  List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
		  permissionList.add(permission);
		  
		  JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissionList));
		  adminPermissionService.addPermisson(permission);
	      
		  NPAdminPermission permission3=new NPAdminPermission();
		  permission3.setPermissionName("all");
		  permission3.setParentId(2);
		  permission3.setId(3);
		  
		  adminPermissionService.addPermisson(permission3);
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
	  }
	  
	  @Test
	  public void testupdatePermisson() throws Exception {
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  NPAdminPermission permission=new NPAdminPermission();
		  permission.setPermissionName("all");
		  permission.setId(1);
		  permission.setParentId(0);
		  PowerMockito.when(adminPermissionMapper.updateByPrimaryKeySelective(permission)).thenReturn(1);
		  adminPermissionService.updatePermisson(permission);
	      Mockito.verify(adminPermissionMapper).updateByPrimaryKeySelective(permission);
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
	  }
	  
	  @Test
	  public void testgetPermissionListByRoleId() throws Exception {
		  
		  NPAdminRole role=new NPAdminRole();
	      Integer roleId=1;
	      
	      List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
		  NPAdminPermission p= new NPAdminPermission();
		  p.setParentId(0);
		  p.setPermissionName("");
		  permissionList.add(p);
		  
		  role.setId(roleId);
		  role.setPermissions(permissionList);
	      JedisUtil.del(Constants.PERMISSON_KEY+roleId);
		  PowerMockito.when(adminRoleMapper.selectByRoleId(roleId)).thenReturn(role);
		  adminPermissionService.getPermissionListByRoleId(roleId);
		  JedisUtil.del(Constants.PERMISSON_KEY+roleId);
	  }
	  
	  @Test
	  public void testgetMenusListByRoleId() throws Exception {
		  
		  NPAdminRole role=new NPAdminRole();
	      Integer roleId=1;
	      
	      List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
		  NPAdminPermission p= new NPAdminPermission();
		  p.setParentId(0);
		  p.setPermissionName("");
		  permissionList.add(p);
		  
		  role.setId(roleId);
		  role.setPermissions(permissionList);
	      JedisUtil.del(Constants.MENU_KEY+roleId);
		  PowerMockito.when(adminRoleMapper.selectMenusByRoleId(roleId)).thenReturn(role);
		  adminPermissionService.getMenusListByRoleId(roleId);
		  JedisUtil.del(Constants.MENU_KEY+roleId);
	  }
	  
	  @Test
	  public void testdeletePermisson() throws Exception {

		  String id="1";
		  String [] ids=id.split(",");
		  PowerMockito.when(adminPermissionMapper.deleteByPrimaryKey(1)).thenReturn(1);
		  PowerMockito.when(adminRolePermissionMapper.deleteByPermissionIds(1)).thenReturn(1);
		  List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
		  NPAdminPermission p= new NPAdminPermission();
		  p.setParentId(0);
		  p.setPermissionName("");
		  p.setId(1);
		  list.add(p);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  adminPermissionService.deletePermisson(ids);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
	  }
	  
	  @Test
	  public void testdeletePermisson1() throws Exception {

		  String id="2";
		  String [] ids=id.split(",");
		  PowerMockito.when(adminPermissionMapper.deleteByPrimaryKey(2)).thenReturn(1);
		  PowerMockito.when(adminRolePermissionMapper.deleteByPermissionIds(2)).thenReturn(1);
		  List<NPAdminPermission> list=new ArrayList<NPAdminPermission>();
		  NPAdminPermission p= new NPAdminPermission();
		  p.setParentId(1);
		  p.setPermissionName("");
		  p.setId(2);
		  list.add(p);
		  
		  NPAdminPermission permission=new NPAdminPermission();
		  permission.setPermissionName("all");
		  permission.setParentId(0);
		  permission.setId(1);
		  
		  NPAdminPermission permission2=new NPAdminPermission();
		  permission2.setPermissionName("haha");
		  permission2.setParentId(1);
		  permission2.setId(2);
		  List<NPAdminPermission> permissionList1=new ArrayList<NPAdminPermission>();
		  permissionList1.add(permission2);
		  
		  permission.setChildPermission(permissionList1);
		  
		  List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
		  permissionList.add(permission);
		  JedisUtil.set(Constants.PERMISSONS_KEY,SerializableUtilOld.serialize(permissionList));
		  PowerMockito.when(adminPermissionMapper.selectByExample(null)).thenReturn(list);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  adminPermissionService.deletePermisson(ids);
		  JedisUtil.del(Constants.PERMISSONS_KEY);
		  JedisUtil.del(Constants.PERMISSONS_NODES);
	  }
	  @Test
	  public void testgetPermissonById() throws Exception {
		  JedisUtil.del(Constants.PERMISSONS_NODES);
		  NPAdminPermission permission=new NPAdminPermission();
		  permission.setPermissionName("permission");
		  Integer id=1;
		  Mockito.when(adminPermissionMapper.selectByPrimaryKey(id)).thenReturn(permission);
		 
		  adminPermissionService.getPermissonById(id);
	      Mockito.verify(adminPermissionMapper).selectByPrimaryKey(id);
	  }
	  
	  @Test
	  public void testGetPermissionList() throws Exception {
		  
		  List<NPAdminPermission> permissionList=new ArrayList<NPAdminPermission>();
	      NPPage page=new NPPage();
	      page.setPage(1);
		  PowerMockito.when(adminPermissionMapper.listPagePermission(page)).thenReturn(permissionList);
		  List<NPAdminPermission> resultList=adminPermissionService.getPermissionList(page);
	      Assert.assertEquals(resultList.size(), permissionList.size());
	      Mockito.verify(adminPermissionMapper).listPagePermission(page);
	  }
}
