package com.awifi.np.admin.tolls4test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/11
 * 创建作者：卢朱娜
 * 文件名称：IndexController2Test.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
////XML风格
////表示使用Spring Test组件进行单元测试
//@RunWith(SpringJUnit4ClassRunner.class)
////使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
//@WebAppConfiguration
////指定Bean的配置文件信息
////@ContextHierarchy({
////        @ContextConfiguration(name = "parent", locations = "classpath:spring/ApplicationContext.xml"),
////        @ContextConfiguration(name = "child", locations = "classpath:spring/ApplicationContext-mvc.xml")
////})
//@ContextConfiguration(locations = {"classpath:spring/ApplicationContext.xml",
//        "classpath:spring/ApplicationContext-mvc.xml",
//        "classpath:spring/ApplicationContext-datasource.xml"})
////配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
////@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
////@Transactional
//@ActiveProfiles("main")
//public class IndexControllerTest {
//    @Autowired
//    private WebApplicationContext wac;
//    private MockMvc mockMvc;
//
////    //如果该对象需要mock，则加上此Annotate
////    @Mock
////    private MailService mailService;
////
////    //使mock对象的使用类可以注入mock对象
////    @InjectMocks
////    MailController mailController;
//
//    @Before
//    public void setUp() {
////        //将打上Mockito标签的对象起作用，使得Mock的类被Mock，使用了Mock对象的类自动与Mock对象关联
////        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//
//    @Test
//    public void indexTest() throws Exception {
//        //得到MvcResult自己验证
//        MvcResult result = mockMvc.perform(get("/admin/login"))
//                .andDo(print())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(content().string(Matchers.contains("成功")))
//                .andExpect(status().isOk())
//                .andReturn();
//
//    }
//}
