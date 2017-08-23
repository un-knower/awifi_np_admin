package com.awifi.np.admin.service;


import com.awifi.np.admin.tolls4test.TestDATA;
import com.awifi.np.admin.dao.NPPlatformServiceMapper;
import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.PlatformServiceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/20
 * 创建作者：卢朱娜
 * 文件名称：BindServiceControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */


//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
//@PowerMockIgnore("javax.management.*")
public class PlatformServiceServiceTest {


    @Mock
    private NPServiceMapper npServiceMapper;

    @Mock
    private NPPlatformServiceMapper platformServiceMapper;

    @InjectMocks
    private PlatformServiceServiceImpl platformServiceService;

    @Before
    public void setUp() {
//        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void testList() throws Exception {
        ArrayList<NPService> serviceArrayList = TestDATA.getServiceList();
        NPPlatform npPlatform = TestDATA.getNPPlatform();

        ArrayList<String> bindedList = TestDATA.getBindedList();
        ArrayList<String> ownedList = TestDATA.getOwnedList();

        PowerMockito.when(npServiceMapper.selectByExample(null)).thenReturn(serviceArrayList);
        PowerMockito.when(platformServiceMapper.selectBindedService(npPlatform.getAppId())).thenReturn(bindedList);
        PowerMockito.when(platformServiceMapper.selectOwnedService(npPlatform.getAppId())).thenReturn(ownedList);


        List<NPService> resultList =  platformServiceService.getServiceList4Bind(npPlatform);

        Assert.assertEquals(resultList.size(), serviceArrayList.size());
        Assert.assertTrue(resultList.get(0).getBinded());
        Assert.assertTrue(resultList.get(0).getOwned());
        Assert.assertTrue(resultList.get(1).getBinded());
        Assert.assertTrue(resultList.get(1).getOwned());
        Assert.assertFalse(resultList.get(2).getBinded());
        Assert.assertFalse(resultList.get(2).getOwned());
        Assert.assertFalse(resultList.get(3).getBinded());
        Assert.assertFalse(resultList.get(3).getOwned());
        Assert.assertTrue(resultList.get(4).getBinded());
        Assert.assertFalse(resultList.get(4).getOwned());
        Assert.assertTrue(resultList.get(5).getBinded());
        Assert.assertFalse(resultList.get(5).getOwned());

    }



}
