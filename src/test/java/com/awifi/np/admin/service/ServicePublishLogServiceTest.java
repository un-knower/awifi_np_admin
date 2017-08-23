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

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPServicePublishLogMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServicePublishLog;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.service.impl.InterfaceTmpServiceImpl;
import com.awifi.np.admin.service.impl.ServicePublishLogServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;

@RunWith(PowerMockRunner.class)
public class ServicePublishLogServiceTest {

	@Mock
	private NPServicePublishLogMapper publishLogMapper;
	@Mock
	private ServiceServiceImpl serviceService;
	@Mock
	private InterfaceTmpServiceImpl interfaceTmpService;
	@Mock
	private InterfaceServiceImpl interfaceService;
	
	@InjectMocks
	private ServicePublishLogServiceImpl servicePublissLogService;

	@Before
	public void setUp() {
	}

	@Test
	public void getJoinInfo() throws Exception {
		NPServicePublishLog log=new NPServicePublishLog();
		PowerMockito.when(publishLogMapper.getJoinInfo(1)).thenReturn(log);
		servicePublissLogService.getJoinInfo(1);
		Mockito.verify(publishLogMapper).getJoinInfo(1);
	}
	
	@Test
	public void update() throws Exception {
		NPServicePublishLog log=new NPServicePublishLog();
		log.setId(1);
		log.setServiceId(1);
		log.setPublishStatus(Constants.PS_TO_BE_CHECK);
		NPService serviceConfig=new NPService();
		NPAdminUser user=new NPAdminUser();
		servicePublissLogService.update(log, serviceConfig, user);
		
		
		log.setPublishStatus(Constants.PS_TO_BE_PUBLISH);
		servicePublissLogService.update(log, serviceConfig, user);
		
		log.setPublishStatus(Constants.PS_CANCEL);
		NPService npService=new NPService();
		npService.setPublishStatus(Constants.SERVICR_PUBLISH_ING);
		PowerMockito.when(serviceService.getById(1)).thenReturn(npService);
		servicePublissLogService.update(log, serviceConfig, user);
		
		npService.setPublishStatus(Constants.SERVICR_UPDATE_ING);
		PowerMockito.when(serviceService.getById(1)).thenReturn(npService);
		servicePublissLogService.update(log, serviceConfig, user);
		
		log.setPublishStatus(Constants.PS_PUBLISHED);
		
		PowerMockito.when(publishLogMapper.getJoinInfo(1)).thenReturn(log);
		servicePublissLogService.update(log, serviceConfig, user);
	}
	
	
	@Test
	public void testList() throws Exception {
		List<NPServicePublishLog> logs=new ArrayList<NPServicePublishLog>();
		NPPage page = new NPPage();
		page.setPage(1);
		PowerMockito.when(publishLogMapper.listPagePublishLog(page)).thenReturn(logs);
		servicePublissLogService.listPagePublishLog(page, "", 1, 1);
		Mockito.verify(publishLogMapper).listPagePublishLog(page);
	}
	
	@Test
	public void testInsert() throws Exception {
		NPServicePublishLog log=new NPServicePublishLog();
		log.setId(1);
		log.setServiceId(1);
		log.setPublishStatus(Constants.PS_TO_BE_CHECK);
		NPService npService=new NPService();
		NPAdminUser user=new NPAdminUser();
		npService.setPublishStatus(Constants.SERVICR_NOT_ON_LINE);
		PowerMockito.when(publishLogMapper.insertSelective(log)).thenReturn(1);
		servicePublissLogService.insert(log, npService, user);
		
		npService.setPublishStatus(Constants.SERVICR_ON_LINE);
		servicePublissLogService.insert(log, npService, user);
		Mockito.verify(publishLogMapper,Mockito.times(2)).insertSelective(log);
	}
	
	
}
