package com.awifi.np.admin.controller.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServicePublishLog;
import com.awifi.np.admin.service.impl.ServicePublishLogServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.tolls4test.TestDATA;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/9
 * 创建作者：卢朱娜
 * 文件名称：PublishLogControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PublishLogControllerTest {
    private String urlprefix = TestDATA.publishUrl;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @InjectMocks
    private PublishLogController controller;
    
    private MockHttpServletRequest request;    
    @Mock
    private ServiceServiceImpl serviceService;
    
    @Mock
    private ServicePublishLogServiceImpl publishLogService;
    @Before
    public void setUp() {
    	 MockitoAnnotations.initMocks(this);
	     this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

   @Test
   public void testlistServicePublishLog() throws Exception{
	   
	   List<NPServicePublishLog> list=new ArrayList<NPServicePublishLog>();
	   NPPage page=new NPPage();
	   when(publishLogService.listPagePublishLog(page," ",1,1)).thenReturn(list);
	   
	   mockMvc.perform(get(TestDATA.publishUrl)
	    .param("sid", "1")
		.param("keyword", "")
		.param("publishStatus", "1")
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
       .andReturn();
   }
    
   
   
   @Test
   public void testgetPublishLog() throws Exception{
	   
	   List<NPServicePublishLog> list=new ArrayList<NPServicePublishLog>();
	   NPServicePublishLog publishLog=new NPServicePublishLog();
	   when(publishLogService.getJoinInfo(11)).thenReturn(publishLog);
	   String s="{'name':'dd'}";
	   publishLog.setConfig(s);
	   mockMvc.perform(get(TestDATA.publishUrl+"/11")
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
       .andReturn();
   }
   
   @Test
   public void testgetPublishLog2() throws Exception{
	   
	   List<NPServicePublishLog> list=new ArrayList<NPServicePublishLog>();
	   NPServicePublishLog publishLog=new NPServicePublishLog();
	   when(publishLogService.getJoinInfo(11)).thenReturn(null);
	   String s="{'name':'dd'}";
	   publishLog.setConfig(s);
	   mockMvc.perform(get(TestDATA.publishUrl+"/11")
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
       .andExpect(jsonPath("$.code").value("E1000008"));
   }
   @Test
   public void testAddPublishLog() throws Exception{
	   NPServicePublishLog publishLog=new NPServicePublishLog();
	  
	   mockMvc.perform(post(TestDATA.publishUrl)
	   .contentType(MediaType.APPLICATION_JSON)
       .content(JSONObject.toJSONString(publishLog))
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   
	   NPService npService =new NPService();
	   publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   npService.setId(1);
	   publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   
	   npService.setServiceHost("http://alpha-np.51awifi.com");
	   publishLog.setNpService(npService);
	   when(serviceService.getById(npService.getId())).thenReturn(npService);
	   when(publishLogService.countINGByServiceId(npService.getId())).thenReturn(0);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   npService.setIsMenu(true);
	   publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   npService.setCheckAuth("/pubsrv/permission/check");
	   publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
       npService.setCheckSafe("/pubsrv/security/check");
       publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
        npService.setMenuTreeApi("test");
       publishLog.setNpService(npService);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
       npService.setRoleMenuApi("test");
       npService.setPublishStatus((byte)0);
       publishLog.setNpService(npService);
       when(serviceService.getById(npService.getId())).thenReturn(npService);
	   when(publishLogService.countINGByServiceId(npService.getId())).thenReturn(0);
	   mockMvc.perform(post(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("0"));
   }
   
   
   @Test
   public void testUpdatePublishLog() throws Exception{
	   NPServicePublishLog publishLog=new NPServicePublishLog();
	  
	   mockMvc.perform(put(TestDATA.publishUrl)
	   .contentType(MediaType.APPLICATION_JSON)
       .content(JSONObject.toJSONString(publishLog))
       .accept(MediaType.APPLICATION_JSON))
       .andDo(print())
       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   publishLog.setId(1);
	   mockMvc.perform(put(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   
	   
	   publishLog.setPublishStatus((byte)1);
	   mockMvc.perform(put(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000008"));
	   
	   
	   publishLog.setPublishStatus((byte)3);
	   mockMvc.perform(put(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000008"));
	   
	   NPService npService =new NPService();
	   publishLog.setNpService(npService);
	   mockMvc.perform(put(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000001"));
	   npService.setId(1);
	   
	   npService.setServiceHost("http://192.168.41.77");
	   npService.setIsMenu(false);
	   npService.setCheckAuth("/pubsrv/permission/check");
       npService.setCheckSafe("/pubsrv/security/check");
       npService.setMenuTreeApi("test");
       npService.setRoleMenuApi("test");
       npService.setPublishStatus((byte)0);
	   publishLog.setNpService(npService);
	   
//	   when(serviceService.getById(npService.getId())).thenReturn(npService);
	   
	   when(publishLogService.getJoinInfo(publishLog.getId())).thenReturn(publishLog);
	   mockMvc.perform(put(TestDATA.publishUrl)
			   .contentType(MediaType.APPLICATION_JSON)
		       .content(JSONObject.toJSONString(publishLog))
		       .accept(MediaType.APPLICATION_JSON))
		       .andDo(print())
		       .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
		       .andExpect(jsonPath("$.code").value("E1000004"));
   }
//    //发起上线，配置测试
//    public static void configAndTest(MockMvc mockMvc, NPService newNPService) throws Exception {
//
//        NPServicePublishLog publishLog = new NPServicePublishLog();
//        publishLog.setNpService(newNPService);
//        String requestBody = JSONObject.toJSONString(publishLog);
//        mockMvc.perform(post(TestDATA.publishUrl)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("0"))
//                .andReturn();
//    }
//
//    //重复发起上线
//    public static void configAndTest_E1014003(MockMvc mockMvc, NPService newNPService) throws Exception {
//        NPServicePublishLog publishLog = new NPServicePublishLog();
//        publishLog.setNpService(newNPService);
//        String requestBody = JSONObject.toJSONString(publishLog);
//        mockMvc.perform(post(TestDATA.publishUrl)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("E1014003"))
//                .andReturn();
//    }
//
//
//    //上线列表
//    public static JSONArray listPublishLog(MockMvc mockMvc, NPService npService, Byte status) throws Exception {
//        RequestBuilder requestBuilder;
//        if(status==null){
//            requestBuilder = get(TestDATA.publishUrl)
//                    .param("page", "1").param("pageSize","10")
//                    .param("keyword", npService.getServiceCode())
//                    .accept(MediaType.APPLICATION_JSON);
//        }else{
//            requestBuilder = get(TestDATA.publishUrl)
//                    .param("page", "1").param("pageSize","10")
//                    .param("keyword", npService.getServiceCode())
//                    .param("publishStatus", String.valueOf(status))
//                    .accept(MediaType.APPLICATION_JSON);
//        }
//
//        String result = mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        JSONObject resultObj = JSONObject.parseObject(result);
//
//        assertEquals(resultObj.getString("code"),"0");
//
//        JSONObject data = resultObj.getJSONObject("data");
//        JSONArray pageList = data.getJSONArray("list");
//        return pageList;
//    }
//
//    //获取详情
//    public static NPServicePublishLog getPublishLogInfo(MockMvc mockMvc, Integer id) throws Exception {
//        String result = mockMvc.perform(get(TestDATA.publishUrl +"/" + id)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andReturn().getResponse().getContentAsString();
//
//        JSONObject resultObj = JSONObject.parseObject(result);
//
//        assertEquals(resultObj.getString("code"),"0");
//
//        return resultObj.getObject("data", NPServicePublishLog.class);
//
//    }
//
//    //更新配置，或者更改上线状态
//    public static void updatePublish(MockMvc mockMvc, NPServicePublishLog publishLog) throws Exception {
//        String requestBody = JSONObject.toJSONString(publishLog);
//        mockMvc.perform(put(TestDATA.publishUrl)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody)
//                .accept(MediaType.APPLICATION_JSON)) //执行请求
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
//                .andExpect(jsonPath("$.code").value("0"))
//                .andReturn();
//    }
//
//
//    public static NPServicePublishLog startNewPublishFlow(MockMvc mockMvc, NPService npService, NPService newNPService) throws Exception {
//
//        //发起，配置，测试
//        configAndTest(mockMvc, newNPService);
//        Byte serviceStausPre = npService.getPublishStatus();
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        if(serviceStausPre.equals(Constants.SERVICR_NOT_ON_LINE)){
//            assertEquals(new Byte(Constants.SERVICR_PUBLISH_ING), npService.getPublishStatus());
//        }else if(serviceStausPre.equals(Constants.SERVICR_ON_LINE)){
//            assertEquals(new Byte(Constants.SERVICR_UPDATE_ING), npService.getPublishStatus());
//        }
//
//        //获取上线日志列表
//        JSONArray logJsonArray = listPublishLog(mockMvc, npService, Constants.PS_TO_BE_CHECK);
//        NPServicePublishLog publishLog_0 = logJsonArray.getObject(0, NPServicePublishLog.class);
//        assertEquals(npService.getServiceCode(), publishLog_0.getServiceCode());
//        assertEquals(new Byte(Constants.PS_TO_BE_CHECK), publishLog_0.getPublishStatus());
//
//
//        //审核通过
//        publishLog_0.setPublishStatus(Constants.PS_TO_BE_PUBLISH);
//        updatePublish(mockMvc, publishLog_0);
//
//        //上线
//        publishLog_0.setPublishStatus(Constants.PS_PUBLISHED);
//        updatePublish(mockMvc, publishLog_0);
//
//        return publishLog_0;
//    }
//
//
//    public static void testANewFlow(MockMvc mockMvc,
//                             InterfaceRegisterDTO interfaceRegisterDTO,
//                             NPService npService, NPService newNPService) throws Exception {
//        //重新注册接口
//        BizUtils.registerInterface(mockMvc, interfaceRegisterDTO, null);
//
//        //发起一个全新的上线流程
//        NPServicePublishLog publishLog_1 = startNewPublishFlow(mockMvc, npService, newNPService);
//
//        //获取详情,上线状态为已上线
//        publishLog_1 = getPublishLogInfo(mockMvc, publishLog_1.getId());
//        assertEquals(publishLog_1.getPublishStatus(), new Byte(Constants.PS_PUBLISHED));
//        assertNotNull(publishLog_1.getPublishDate());
//        //服务的配置已更新
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        assertEquals(new Byte(Constants.SERVICR_ON_LINE), npService.getPublishStatus());
//        assertEquals(newNPService.getVersion(), npService.getVersion());
//
//
//        //获取临时表中的接口，接口已删除
//        JSONArray jsonArray = TmpInterfaceControllerTest.listTmpInterface(mockMvc, npService.getId());
//        assertSame(0, jsonArray.size());
//
//        //接口表中的接口，接口已添加
//        NPPage page = BizUtils.gererateNPPage(1,100,"keyword",null,"serviceName",npService.getServiceName());
//        jsonArray = BizUtils.getPageList(mockMvc, TestDATA.interfaceUrl, page);
//        assertSame(2, jsonArray.size());
//    }
//
//
//
//
//
//    /**
//     * 1、模拟一个服务，注册2个临时接口（未上线）
//     * 2、发起上线，配置测试
//     * 3、获取上线日志列表
//     * 4、配置接口
//     * 5、配置测试服务
//     * 6、获取日志详情
//     * 7、修改配置
//     * 8、审核服务
//     * 9、取消上线（清除临时表中的接口，更改服务状态）
//     * 10、重新发起上线
//     * 11、配置服务、配置接口、审核服务、上线服务
//     * 12、重新发起上线、升级服务
//     *
//     */
//    @Test
//    public void publishTest() throws Exception {
//        //模拟一个服务，注册2个临时接口
//        InterfaceRegisterDTO interfaceRegisterDTO = TestDATA.getInterfaceRegisterDTO();
//        JSONArray serviceJsonArray =
//                BizUtils.registerInterface(mockMvc, interfaceRegisterDTO, TestDATA.getService());
//
//        NPService npService = serviceJsonArray.getObject(0, NPService.class);
//
//        //发起上线，配置测试
//        NPService newNPService = new NPService();
//        newNPService.setId(npService.getId());
//        newNPService.setServiceHost("https://api.weixin.qq.com");
//        newNPService.setCheckAuth("/cgi-bin/token");
//        newNPService.setCheckSafe("/datacube/getusersummary");
//        newNPService.setIsMenu(false);
//        newNPService.setVersion("V1");
//        configAndTest(mockMvc, newNPService);
//
//        //获取上线日志列表
//        JSONArray logJsonArray = listPublishLog(mockMvc, npService, Constants.PS_TO_BE_CHECK);
//        NPServicePublishLog publishLog_0 = logJsonArray.getObject(0, NPServicePublishLog.class);
//        assertEquals(npService.getServiceCode(), publishLog_0.getServiceCode());
//        assertEquals(new Byte(Constants.PS_TO_BE_CHECK), publishLog_0.getPublishStatus());
//
//        //获取详情
//        publishLog_0 = getPublishLogInfo(mockMvc, publishLog_0.getId());
//
//        //重新配置
//        updatePublish(mockMvc, publishLog_0);
//
//        //获取服务详情
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        assertEquals(new Byte(Constants.SERVICR_PUBLISH_ING), npService.getPublishStatus());
//
//        //审核通过
//        publishLog_0.setPublishStatus(Constants.PS_TO_BE_PUBLISH);
//        updatePublish(mockMvc, publishLog_0);
//
//        //获取详情
//        publishLog_0 = getPublishLogInfo(mockMvc, publishLog_0.getId());
//        assertEquals(publishLog_0.getPublishStatus(), new Byte(Constants.PS_TO_BE_PUBLISH));
//        assertNotNull(publishLog_0.getCheckDate());
//
//
//        //重新发起上线, 报错E1014003=不能重复发起上线
//        configAndTest_E1014003(mockMvc, newNPService);
//
//        //取消升级
//        publishLog_0.setPublishStatus(Constants.PS_CANCEL);
//        updatePublish(mockMvc, publishLog_0);
//
//        //获取详情
//        publishLog_0 = getPublishLogInfo(mockMvc, publishLog_0.getId());
//        assertEquals(publishLog_0.getPublishStatus(), new Byte(Constants.PS_CANCEL));
//        assertNotNull(publishLog_0.getCancelDate());
//
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        assertEquals(new Byte(Constants.SERVICR_NOT_ON_LINE), npService.getPublishStatus());
//
//        //获取临时表中的接口
//        JSONArray jsonArray = TmpInterfaceControllerTest.listTmpInterface(mockMvc, npService.getId());
//        assertSame(0, jsonArray.size());
//
//
//
//        //---------------------------------发起一次新的上线流程----------------------------------
//        //获取服务详情
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        testANewFlow(mockMvc, interfaceRegisterDTO, npService, newNPService);
//
//
//        //-------------------------------发起一次升级流程--------------------------------------
//        newNPService.setVersion("V2");
//        npService = BizUtils.getServiceById(mockMvc, npService.getId());
//        testANewFlow(mockMvc, interfaceRegisterDTO, npService, newNPService);
//
//    }

}
