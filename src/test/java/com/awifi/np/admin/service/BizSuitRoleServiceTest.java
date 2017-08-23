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
import org.springframework.beans.factory.annotation.Autowired;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPBizRoleMapper;
import com.awifi.np.admin.dao.NPBizSuitRoleMapper;
import com.awifi.np.admin.dao.NPBizUserMapper;
import com.awifi.np.admin.dao.NPBizUserRoleMapper;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizSuitRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.BizSuitRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserRoleServiceImpl;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;
import com.awifi.np.admin.utils.MessageUtil;

@RunWith(PowerMockRunner.class)
public class BizSuitRoleServiceTest {

	@Mock
	private NPBizSuitRoleMapper bizSuitRoleMapper;
	@Mock
	private NPBizRoleMapper bizRoleMapper;

	@InjectMocks
	private BizSuitRoleServiceImpl suitService;

	@Before
	public void setUp() {
	}

	@Test
	public void saveBizRoleSuits() throws Exception {
		Assert.assertEquals(MessageUtil.returnError(Constants.ECodeParam,"suitId"),suitService.saveBizRoleSuits(null, null));
		
	}
	
	
	@Test
	public void saveBizRoleSuits1() throws Exception {
		List<NPBizSuitRole> suitIdList=new ArrayList<NPBizSuitRole>();
		NPBizSuitRole role=new NPBizSuitRole();
		role.setRoleId(1L);
		suitIdList.add(role);
		Assert.assertEquals(MessageUtil.returnError(Constants.ECodeParam,"roleId"),suitService.saveBizRoleSuits(null, suitIdList));
		
	}
	
	@Test
	public void saveBizRoleSuits2() throws Exception {
		List<NPBizSuitRole> suitIdList=new ArrayList<NPBizSuitRole>();
		NPBizSuitRole role=new NPBizSuitRole();
		role.setRoleId(1L);
		suitIdList.add(role);
		Long roleId=11L;
		PowerMockito.when(bizRoleMapper.selectByPrimaryKey(roleId)).thenReturn(null);
		Assert.assertEquals(MessageUtil.returnError("E1017001"),suitService.saveBizRoleSuits(roleId, suitIdList));
		
	}
	
	@Test
	public void saveBizRoleSuits3() throws Exception {
		List<NPBizSuitRole> suitIdList=new ArrayList<NPBizSuitRole>();
		NPBizSuitRole role=new NPBizSuitRole();
		role.setRoleId(1L);
		suitIdList.add(role);
		Long roleId=11L;
		NPBizRole bizRole=new NPBizRole();
		bizRole.setId(11L);
		PowerMockito.when(bizRoleMapper.selectByPrimaryKey(11L)).thenReturn(bizRole);
		Assert.assertEquals(MessageUtil.returnSuccess(),suitService.saveBizRoleSuits(11L, suitIdList));
		
	}
	
	
	@Test
	public void insertRoleSuits() throws Exception {
		List<NPBizSuitRole> suitIdList=new ArrayList<NPBizSuitRole>();
		NPBizSuitRole role=new NPBizSuitRole();
		role.setRoleId(1L);
		suitIdList.add(role);
		Long roleId=11L;
		NPBizRole bizRole=new NPBizRole();
		bizRole.setId(11L);
		PowerMockito.when(bizRoleMapper.selectByPrimaryKey(11L)).thenReturn(bizRole);
		Assert.assertEquals(MessageUtil.returnSuccess(),suitService.saveBizRoleSuits(11L, suitIdList));
		
	}
}
