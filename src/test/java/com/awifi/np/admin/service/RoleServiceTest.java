package com.awifi.np.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminRolePermissionMapper;
import com.awifi.np.admin.dao.NPAdminUserMapper;
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.AdminPermissionServiceImpl;
import com.awifi.np.admin.service.impl.AdminRoleServiceImpl;

@RunWith(PowerMockRunner.class)
public class RoleServiceTest {

	@Mock
	private NPAdminRoleMapper roleMapper;

	@Mock
	private NPAdminRolePermissionMapper rolePermissonMapper;

	@Mock
	private NPAdminUserMapper userMapper;

	@Mock
	private AdminPermissionServiceImpl adminPermissionService;

	@InjectMocks
	private AdminRoleServiceImpl adminRoleService;
	
	
	@Before
	public void setUp() {
		// MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testList() throws Exception {
		// ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
		ArrayList<NPAdminRole> list = new ArrayList<>();
		list.add(new NPAdminRole());
		NPPage page = new NPPage();
		page.setPage(1);
		PowerMockito.when(roleMapper.listPageRoles(page)).thenReturn(list);
		List<NPAdminRole> resultList = adminRoleService.listRoles(page, null);
	}
	
	@Test
	public void allRoleList() throws Exception {
		// ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
		ArrayList<NPAdminRole> list = new ArrayList<>();
		list.add(new NPAdminRole());
		
		PowerMockito.when(roleMapper.selectByExample(null)).thenReturn(list);
		adminRoleService.allRoleList();
		
	}
	
	@Test
	public void add() throws Exception {
		  
		  NPAdminPermission permission2=new NPAdminPermission();
		  permission2.setPermissionName("haha");
		  permission2.setParentId(1);
		  permission2.setId(2);
		  List<NPAdminPermission> permissionList1=new ArrayList<NPAdminPermission>();
		  permissionList1.add(permission2);
		
		NPAdminRole role =new NPAdminRole();
		role.setId(99999);
		role.setPermissions(permissionList1);
		PowerMockito.when(roleMapper.insertSelective(role)).thenReturn(1);
		adminRoleService.addRole(role);
		
	}
	
	
	@Test
	public void update() throws Exception {
		  
		  NPAdminPermission permission2=new NPAdminPermission();
		  permission2.setPermissionName("haha");
		  permission2.setParentId(1);
		  permission2.setId(2);
		  List<NPAdminPermission> permissionList1=new ArrayList<NPAdminPermission>();
		  permissionList1.add(permission2);
		
		NPAdminRole role =new NPAdminRole();
		role.setId(99999);
		role.setPermissions(permissionList1);
		PowerMockito.when(roleMapper.updateByPrimaryKeySelective(role)).thenReturn(1);
		adminRoleService.updateRole(role);
		
	}
	
	
	@Test
	public void getRoleById() throws Exception {
		// ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
		Integer id = 9999;
		NPAdminRole role = new NPAdminRole();
		role.setId(id);

		NPAdminPermission permission2 = new NPAdminPermission();
		permission2.setPermissionName("haha");
		permission2.setParentId(1);
		permission2.setId(2);
		List<NPAdminPermission> permissionList1 = new ArrayList<NPAdminPermission>();
		permissionList1.add(permission2);
		role.setPermissions(permissionList1);
		PowerMockito.when(roleMapper.selectByPrimaryKey(id)).thenReturn(role);
//		PowerMockito.when(roleMapper.selectByRoleId(id)).thenReturn(role);
		PowerMockito.when(adminPermissionService.getPermissionListByRoleId(id)).thenReturn(permissionList1);
		adminRoleService.getRoleById(id);
	}
	
	
	 @Test
	  public void testdelete() throws Exception {
		  String ids="1";
		  adminRoleService.deteleByIds(ids);
	  }
}
