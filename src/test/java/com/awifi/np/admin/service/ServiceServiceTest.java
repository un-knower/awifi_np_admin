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
import org.springframework.beans.factory.annotation.Autowired;

import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;
import com.awifi.np.admin.service.impl.PlatformServiceServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;

@RunWith(PowerMockRunner.class)
public class ServiceServiceTest {

	@Mock
	private NPServiceMapper serviceMapper;
	@Mock
	private PlatformServiceServiceImpl platformServiceService;
	
	@InjectMocks
	private ServiceServiceImpl serviceService;
	
	@Before
	public void setUp() {
	}

	@Test
	public void insert() throws Exception {
		NPService service=new NPService();
		service.setServiceCode("2222");
		serviceService.insert(service);
	}
	
	
	@Test
	public void insert2() throws Exception {
		NPPlatform npPlatform=new NPPlatform();
		npPlatform.setAppId("11");
		NPAdminUser adminUser=new NPAdminUser();
		NPService service=new NPService();
		service.setServiceCode("2222");
		
		PowerMockito.doNothing().when(platformServiceService).insert(npPlatform.getAppId(),"2222" ,true);
		serviceService.insert(service, npPlatform, adminUser);
		Mockito.verify(platformServiceService).insert(npPlatform.getAppId(),"2222" ,true);
		
	}
	
	
	@Test
	public void updateAndBindPlatform() throws Exception {
		NPPlatform npPlatform=new NPPlatform();
		npPlatform.setAppId("11");
		NPAdminUser adminUser=new NPAdminUser();
		NPService service=new NPService();
		service.setServiceCode("2222");
		service.setId(11);
		PowerMockito.doNothing().when(platformServiceService).insert(npPlatform.getAppId(),"2222" ,true);
		serviceService.updateAndBindPlatform(service, npPlatform);
		Mockito.verify(platformServiceService).insert(npPlatform.getAppId(),"2222" ,true);
		
	}
	
	@Test
	public void updateStatus() throws Exception {
		serviceService.updateStatus(1, (byte)0);
	}
	
	
	@Test
	public void listOnlineService() throws Exception {
		serviceService.listOnlineService(true);
		serviceService.listOnlineService(false);
	}
	
	
}
