/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月22日 下午8:20:17
* 创建作者：王冬冬
* 文件名称：AdminLogServiceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPAdminLogMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.service.impl.AdminLogServiceImpl;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.RequestUtil;
import com.awifi.np.admin.utils.SessionUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestUtil.class,SessionUtil.class})
public class AdminLogServiceTest {

    @InjectMocks
    AdminLogServiceImpl logService;
    
    @Mock
    private NPAdminLogMapper adminLogMapper;
    
    @Mock
    MockHttpServletRequest request;
    
    @Mock
    MockHttpServletResponse response;
    @Before
    public void setUp() {
        PowerMockito.mockStatic(RequestUtil.class);
        PowerMockito.mockStatic(SessionUtil.class);
    }
    @Test
    public void test(){
        PowerMockito.when(RequestUtil.getIpAddr(anyObject())).thenReturn("result");
        NPAdminUser adminUser=new NPAdminUser();
        PowerMockito.when(SessionUtil.getSessionUser(anyObject())).thenReturn(adminUser);
        logService.createAdminLog(request, response, new Exception(), 0L, 1L);
        
    }
}
