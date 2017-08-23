package com.awifi.np.admin.tolls4test;

import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.utils.PropertiesUtil;
import org.junit.Before;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.MockRestServiceServer.createServer;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/10
 * 创建作者：卢朱娜
 * 文件名称：AbstractRestTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public abstract class AbstractRestMockServerTest {

    public static RestTemplate restTemplate;
    public MockRestServiceServer mockServer;


    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        mockServer = createServer(restTemplate);
    }

}
