/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月13日 下午3:09:50
* 创建作者：王冬冬
* 文件名称：BizRoleInterfaceServiceImplTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizUserServiceImpl;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.NPAdminUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClientUtil.class,NPAdminUtil.class})
@SuppressStaticInitializationFor("com.awifi.np.admin.utils.HttpClientUtil")//阻止静态代码块运行 
public class BizRoleInterfaceServiceImplTest {
    @Mock
    private InterfaceServiceImpl interfaceService;
    @InjectMocks
    private BizRoleInterfaceServiceImpl bizRoleInterfaceServiceImpl;
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(HttpClientUtil.class);
        PowerMockito.mockStatic(NPAdminUtil.class);
    }

    @Test
    public void testgetRoleInterfaceRelation() throws Exception {
        
        List<NPService> serviceList=new ArrayList<NPService>();
        NPService service=new NPService();
        service.setServiceHost("localhost");
        service.setServicePort("80");
        service.setRolePermissionApi("test");
        service.setServiceCode("serviceCode");
        serviceList.add(service);
        Long roleId=1L;
        
        
        List<String> okCodeList=new ArrayList<>();
        okCodeList.add("hello");
        JSONObject json=new JSONObject();
        json.put("code", "0");
        json.put("data", JSONObject.toJSONString(okCodeList));
        PowerMockito.when(HttpClientUtil.get(anyString(), anyObject())).thenReturn(json.toJSONString());
        List<NPInterface> allInterface=new ArrayList<>();
        NPInterface npinterface=new NPInterface();
        npinterface.setId(1);
        npinterface.setInterfaceCode("code");
        npinterface.setInterfaceName("name");
        allInterface.add(npinterface);
        PowerMockito.when(interfaceService.listRegistedByServiceCode(eq(service.getServiceCode()),anyList())).thenReturn(allInterface);
        
        JSONObject result=bizRoleInterfaceServiceImpl.getRoleInterfaceRelation(roleId, serviceList);
        Assert.assertEquals("0", result.get("code"));
    }
    
}
