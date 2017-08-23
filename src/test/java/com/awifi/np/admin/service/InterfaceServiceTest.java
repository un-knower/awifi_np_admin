/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月22日 下午2:01:22
* 创建作者：王冬冬
* 文件名称：InterfaceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.service;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyInt;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPInterfaceMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPInterfaceCriteria;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.NPAdminUtil;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;


@RunWith(PowerMockRunner.class)
@PrepareForTest({JedisUtil.class})
public class InterfaceServiceTest {
    
    
    @Mock
    private NPInterfaceMapper interfaceMapper;
    @InjectMocks
    private InterfaceServiceImpl interfaceService;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(JedisUtil.class);
    }

    @Test
    public void test_listPageInterface() {
        List<NPInterface> list=new ArrayList<NPInterface>();
        NPPage page=new NPPage();
        PowerMockito.when(interfaceMapper.listPageInterface(anyObject())).thenReturn(list);

        List<NPInterface> results=interfaceService.listPageInterface(page, "keyword", "test_service");
        Assert.assertEquals(list, results);
    }

    @Test
    public void test_getByKey() throws Exception {
        NPInterface npinterface=new NPInterface();
        PowerMockito.when(interfaceMapper.getByKey(anyInt())).thenReturn(npinterface);
        NPInterface result=interfaceService.getByKey(11);
        Assert.assertEquals(npinterface, result);
    }

    @Test
    public void insert() throws Exception {

        List<NPInterface> tmpList = new ArrayList<NPInterface>();
        PowerMockito.when(interfaceMapper.selectByExample(anyObject())).thenReturn(tmpList);
        PowerMockito.when(interfaceMapper.insert(anyObject())).thenReturn(1);
        NPInterface npInterface=new NPInterface();
        npInterface.setInterfaceCode("interface");
        npInterface.setServiceCode("servicecode");
        NPAdminUser usr=new NPAdminUser();
        String result= interfaceService.insert(npInterface, usr);
        Assert.assertEquals("0", result);
    }
//
//
    @Test
    public void update() throws Exception {
        List<NPInterface> tmpList = new ArrayList<NPInterface>();
        PowerMockito.when(interfaceMapper.selectByExample(anyObject())).thenReturn(tmpList);
        PowerMockito.when(interfaceMapper.updateByPrimaryKeySelective(anyObject())).thenReturn(1);
        NPInterface npInterface=new NPInterface();
        npInterface.setInterfaceCode("interface");
        npInterface.setServiceCode("servicecode");
        npInterface.setId(1);
        NPAdminUser usr=new NPAdminUser();
        String result= interfaceService.update(npInterface, usr);
        Assert.assertEquals("0", result);
    }
//
//
    @Test
    public void delete() throws Exception {
        NPInterface npinterface=new NPInterface();
        PowerMockito.when(interfaceMapper.getByKey(anyInt())).thenReturn(npinterface);
        NPInterface result=interfaceService.getByKey(11);
        result.setServiceCode("servicecode");
        result.setInterfaceCode("interfacecode");
        PowerMockito.when(interfaceMapper.deleteByPrimaryKey(anyInt())).thenReturn(1);
        PowerMockito.doNothing().when(JedisUtil.class,"del",anyString());
        interfaceService.delete(11);
        PowerMockito.verifyStatic();
    }
//
//
    @Test
    public void deleteByServiceCode() throws Exception {
        NPInterface npinterface=new NPInterface();
        npinterface.setServiceCode("xxxx");
        PowerMockito.when(interfaceMapper.deleteByExample(anyObject())).thenReturn(1);
        interfaceService.deleteByServiceCode(npinterface.getServiceCode());
        Mockito.verify(interfaceMapper).deleteByExample(anyObject());
    }
//   
    @Test
    public void insertFromTmp() throws Exception {
        PowerMockito.doNothing().when(interfaceMapper).insertFromTmp("xxx");
        interfaceService.insertFromTmp("xxx");
        Mockito.verify(interfaceMapper).insertFromTmp("xxx");
    }

    @Test
    public void test_listRegistedByServiceCode() throws Exception {
        List<NPInterface> list=new ArrayList<NPInterface>();
        PowerMockito.when(interfaceMapper.selectByExample(anyObject())).thenReturn(list);
        List<NPInterface> result=interfaceService.listRegistedByServiceCode("xxx");
        Assert.assertEquals(list, result);
    }

    @Test
    public void test_listRegistedByServiceCode2() throws Exception {
        List<NPInterface> list=new ArrayList<NPInterface>();
        List<String> okCodeList=new ArrayList<String>();
        PowerMockito.when(interfaceMapper.listRegistedByServiceCode(anyObject())).thenReturn(list);
        List<NPInterface> result=interfaceService.listRegistedByServiceCode("xxx",okCodeList);
        Assert.assertEquals(list, result);
    }
}
