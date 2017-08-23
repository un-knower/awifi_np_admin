/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年6月9日 上午8:57:09
* 创建作者：王冬冬
* 文件名称：TemplateServciceTest.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
package com.awifi.np.admin.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.dao.NPTemplateMapper;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPTemplate;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.service.impl.TemplateServiceImpl;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClientUtil.class,JedisUtil.class})
@SuppressStaticInitializationFor("com.awifi.np.admin.utils.HttpClientUtil")//阻止静态代码块运行 
public class TemplateServciceTest {
    @Mock
    private NPTemplateMapper templateMapper;
    
    @Mock
    private NPServiceMapper serviceMapper;
    
    @Mock
    private ServiceServiceImpl serviceService;

    @InjectMocks
    private TemplateServiceImpl templateService;
    
    
    @Before
    public void setUp() {
        // MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HttpClientUtil.class);
        PowerMockito.mockStatic(JedisUtil.class);
    }

    @Test
    public void testSelectByParam() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setContent("testTemplate");
        list.add(template);
        PowerMockito.when(templateMapper.selectByExample(anyObject())).thenReturn(list);
        NPTemplate result=templateService.selectByParam("s_dev", "super_admin1", "test");
        Assert.assertEquals(template, result);
    }
    
    @Test
    public void testlistPageTemplates() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setContent("testTemplate");
        template.setServiceCode("s_dev");
        list.add(template);
        Map<String,String> serviceMap=new HashMap<String,String>();
        serviceMap.put("s_dev","设备服务");
        PowerMockito.when(serviceService.getServiceMap(anyObject())).thenReturn(serviceMap);
        PowerMockito.when(templateMapper.listPageTemplates(anyObject())).thenReturn(list);
       
        for(NPTemplate t:list){
            t.setServiceName(serviceMap.get(t.getServiceCode().trim()));
        }
        NPPage page=new NPPage();
        page.setPage(11);
        List<NPTemplate> result=templateService.listPageTemplates(page);
        Assert.assertEquals(list, result);
    }
    
    
    @Test
    public void testaddTemplate() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setContent("testTemplate");
        list.add(template);
        NPService service=new NPService();
        service.setServiceHost("localhost");
        service.setServicePort("8080");
        service.setTemplateCrud("template/add");
        service.setServiceCode("s_dev");
        service.setServiceKey("sjslsjlsjls");
        String responseBody="{'code':'0'}";
        PowerMockito.when(HttpClientUtil.post(anyObject(), anyObject(), anyObject())).thenReturn(responseBody);
        PowerMockito.doNothing().when(JedisUtil.class,"set",anyString(),anyString());
        int result=templateService.addTemplate(template,service);
        Assert.assertEquals(1, result);
    }
    
    @Test
    public void testupdateTemplate() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setContent("testTemplate");
        list.add(template);
        NPService service=new NPService();
        service.setServiceHost("localhost");
        service.setServicePort("8080");
        service.setTemplateCrud("/template/update");
        service.setServiceCode("s_dev");
        service.setServiceKey("sjslsjlsjls");
        String responseBody="{'code':'0'}";
        PowerMockito.when(HttpClientUtil.put(anyObject(), anyObject(), anyObject())).thenReturn(responseBody);
        PowerMockito.doNothing().when(JedisUtil.class,"set",anyString(),anyString());
        int result=templateService.updateTemplate(template,service);
        Assert.assertEquals(1, result);
    }
    
    
    @Test
    public void testgetTemplate() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setServiceCode("s_dev");
        template.setTemplateCode("templatecode");
        template.setSuitCode("suitcode");
        template.setContent("testTemplate");
        template.setId(1);
//        list.add(template);
        NPService service=new NPService();
        service.setServiceHost("localhost");
        service.setServicePort("8080");
        service.setTemplateCrud("/template/get");
        service.setServiceCode("s_dev");
        service.setServiceKey("sjslsjlsjls");
        
        List<NPService> services=new ArrayList<NPService>();
        services.add(service);
        
        String responseBody="{'code':'0','data':{'src':'','content':''}}";
        PowerMockito.when(templateMapper.selectByPrimaryKey(template.getId())).thenReturn(template);
        
        PowerMockito.when(serviceMapper.selectByExample(anyObject())).thenReturn(services);
        PowerMockito.when(HttpClientUtil.get(anyObject(), anyObject())).thenReturn(responseBody);
        NPTemplate result=templateService.getTemplate(1);
        Assert.assertEquals("", result.getContent());
        Assert.assertEquals("", result.getSrc());
    }
    
    @Test
    public void testdeleteTemplate() throws Exception {
        List<NPTemplate> list=new ArrayList<>();
        NPTemplate template=new NPTemplate();
        template.setServiceCode("s_dev");
        template.setTemplateCode("templatecode");
        template.setSuitCode("suitcode");
        template.setContent("testTemplate");
        template.setId(1);
//        list.add(template);
        NPService service=new NPService();
        service.setServiceHost("localhost");
        service.setServicePort("8080");
        service.setTemplateCrud("/template/delete");
        service.setServiceCode("s_dev");
        service.setServiceKey("sjslsjlsjls");
        
        List<NPService> services=new ArrayList<NPService>();
        services.add(service);
        
        String responseBody="{'code':'0','data':{'src':'','content':''}}";
        PowerMockito.when(templateMapper.selectByPrimaryKey(template.getId())).thenReturn(template);
        PowerMockito.when(serviceMapper.selectByExample(anyObject())).thenReturn(services);
        PowerMockito.when(HttpClientUtil.delete(anyObject(), anyObject())).thenReturn(responseBody);
        templateService.deleteTemplate(1);
        PowerMockito.doNothing().when(JedisUtil.class,"del",anyString());
        PowerMockito.verifyStatic();
    }
}
