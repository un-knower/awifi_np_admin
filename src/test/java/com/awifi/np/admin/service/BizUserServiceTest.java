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

import com.awifi.np.admin.dao.NPBizUserMapper;
import com.awifi.np.admin.dao.NPBizUserRoleMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPBizUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.BizUserRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;

@RunWith(PowerMockRunner.class)
public class BizUserServiceTest {

	
	@Mock
	private NPBizUserMapper npBizUserMapper;
	@Mock
	private NPBizUserRoleMapper npBizUserRoleMapper;
	@Mock
	private BizUserRoleServiceImpl bizUserRoleService;

	@InjectMocks
	private BizUserServiceImpl bizUserService;

	@Before
	public void setUp() {
	}

	@Test
	public void testList() throws Exception {
		List<Long> list=new ArrayList<Long>();
		list.add(2L);
		NPPage page = new NPPage();
		page.setPage(1);
		PowerMockito.when(npBizUserMapper.listPageBizUserId(page)).thenReturn(list);
		bizUserService.listPageBizUserId(page, null, null, null, null, null);
		Mockito.verify(npBizUserMapper).listPageBizUserId(page);
	}
	
	@Test
	public void listByIds() throws Exception {
		List<Long> list=new ArrayList<Long>();
		list.add(2L);
		bizUserService.listByIds(list);
	}
	@Test
	public void getById() throws Exception {
		NPBizUser user=new NPBizUser();
		PowerMockito.when(npBizUserMapper.selectByPrimaryKey(11L)).thenReturn(user);
		bizUserService.getById(11L);
		Mockito.verify(npBizUserMapper).selectByPrimaryKey(11L);
	}
	
	@Test
	public void countByUserName() throws Exception {
		bizUserService.countByUserName("dd");
	}
	
	@Test
	public void updateUser() throws Exception {
		List<Long> list=new ArrayList<Long>();
		list.add(2L);
		NPBizUser npBizUser=new NPBizUser();
		npBizUser.setId(1L);
		npBizUser.setUserName("dd");
		npBizUser.setRoleIdList(list);
		NPAdminUser adminUser=new NPAdminUser();
		PowerMockito.when(npBizUserMapper.updateByPrimaryKeySelective(npBizUser)).thenReturn(1);
		PowerMockito.doNothing().when(bizUserRoleService).deleteByUserId(npBizUser.getId());
		
		bizUserService.updateBizUser(npBizUser, adminUser);
		Mockito.verify(npBizUserMapper).updateByPrimaryKeySelective(npBizUser);
		Mockito.verify(bizUserRoleService).deleteByUserId(npBizUser.getId());
	}
	
	@Test
	public void addUser() throws Exception {
		List<Long> list=new ArrayList<Long>();
		list.add(2L);
		NPBizUser npBizUser=new NPBizUser();
		npBizUser.setId(1L);
		npBizUser.setUserName("dd");
		npBizUser.setRoleIdList(list);
		NPAdminUser adminUser=new NPAdminUser();
		PowerMockito.when(npBizUserMapper.insertSelective(npBizUser)).thenReturn(1);
		
		bizUserService.addBizUser(npBizUser, adminUser);
		Mockito.verify(npBizUserMapper).insertSelective(npBizUser);
	}
	
	@Test
	public void resetPassword() throws Exception {
		NPBizUser npBizUser=new NPBizUser();
		npBizUser.setId(1L);
		npBizUser.setUserName("dd");
		NPAdminUser adminUser=new NPAdminUser();
		bizUserService.resetPassword(npBizUser, adminUser);
		
	}
	
	@Test
	public void deleteById() throws Exception {
		Long id=1L;
		PowerMockito.when(npBizUserMapper.deleteByPrimaryKey(id)).thenReturn(1);
		PowerMockito.doNothing().when(bizUserRoleService).deleteByUserId(id);
		bizUserService.deleteById(id);
		Mockito.verify(npBizUserMapper).deleteByPrimaryKey(id);
		Mockito.verify(bizUserRoleService).deleteByUserId(id);
	}
	
	
}
