package com.awifi.np.admin.tolls4test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.utils.PropertiesUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/10
 * 创建作者：卢朱娜
 * 文件名称：MockServerTest.java
 * 版本：  v1.0
 * 功能： 模拟服务端返回
 * 修改记录：
 */
//public class MockServerTest extends AbstractRestMockServerTest {
////    private MockRestServiceServer mockServer;
//    public static String baseUri = PropertiesUtil.getConfig("config","maindomain");
//
//
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//        //模拟一个服务器
////        mockServer = createServer(restTemplate);
//    }
//
//    @Test
//    public void testMenus(){
//        String uri = baseUri + "login";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", 1);
//        jsonObject.put("username", "zzwzzbzwbz");
//        String userJson = jsonObject.toJSONString();
//
//        //添加服务器端断言
//        mockServer
//                .expect(requestTo(uri))
//                .andExpect(method(HttpMethod.GET))
////                .andRespond(withSuccess().contentType(MediaType.APPLICATION_JSON));
//                .andRespond(withSuccess(userJson, MediaType.APPLICATION_JSON));
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(content().string(expectedContent))
////                .andRespond(withStatus(HttpStatus.OK).body(new byte[0]).contentType(MediaType.APPLICATION_JSON));
//
//        //entity的返回和服务器端的断言一样
//        ResponseEntity<String> entity = restTemplate.getForEntity(uri, String.class);
//
//        System.out.print(entity);
////        assertEquals(HttpStatus.OK, entity.getStatusCode());
////        assertThat(entity.getHeaders().getContentType().toString(), containsString(MediaType.APPLICATION_JSON_VALUE));
//
//        mockServer.verify();
//
//
//
//    }
//
//}
