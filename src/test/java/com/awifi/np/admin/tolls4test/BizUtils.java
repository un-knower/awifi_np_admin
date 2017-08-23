package com.awifi.np.admin.tolls4test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.bizrole.BizRoleControllerTest;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/10
 * 创建作者：卢朱娜
 * 文件名称：BizUtils.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class BizUtils {

    /**
     * 模拟添加一个平台
     *
     */
    public static NPPlatform insertPlatform(MockMvc mockMvc, NPPlatform npPlatform) throws Exception {
//        NPPlatform npPlatform = TestDATA.getNPPlatform();
        String  requestBody = JSONObject.toJSONString(npPlatform);
        mockMvc.perform(post(TestDATA.platformUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();
        String result = mockMvc.perform(get(TestDATA.platformUrl).param("page", "1").param("pageSize","3").param("keyword", npPlatform.getAppId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();
        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        JSONObject data = resultObj.getJSONObject("data");
//        assertSame(data.getIntValue("totalRecord"), 1);
        JSONArray pageList = data.getJSONArray("list");
        npPlatform = pageList.getObject(0, NPPlatform.class);

        return npPlatform;
    }


    /**
     * 模拟服务
     * @param mockMvc
     * @param count
     * @return
     * @throws Exception
     */
    public static JSONArray insertService(MockMvc mockMvc, ArrayList<NPService> serviceArrayList, int count) throws Exception {

        if(serviceArrayList==null || count==0){
            return null;
        }

        NPPlatform npPlatform = insertPlatform(mockMvc, TestDATA.getNPPlatform());
//        ArrayList<NPService> serviceArrayList = getServiceList();
        for(int i=0;i<count;i++){
            NPService service = serviceArrayList.get(i);
            service.setPid(npPlatform.getId());
            String requestBody = JSONObject.toJSONString(service);

            mockMvc.perform(post(TestDATA.serviceUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
                    .accept(MediaType.APPLICATION_JSON)) //执行请求
                    .andDo(print())
                    .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                    .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                    .andReturn();
        }

        String prefix = serviceArrayList.get(0).getServiceCode();
        prefix = prefix.substring(0,prefix.length()-1);

        String result = mockMvc.perform(get(TestDATA.serviceUrl)
                .param("page", "1")
                .param("pageSize", String.valueOf(count+1))
                .param("keyword", prefix)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        JSONObject data = resultObj.getJSONObject("data");
        assertSame(count, data.getIntValue("totalRecord"));
        JSONArray pageList = data.getJSONArray("list");
        assertEquals(count, pageList.size());
        return pageList;

    }

    /**
     * 模拟服务，注册接口
     * @param mockMvc
     * @param interfaceRegisterDTO
     * @throws Exception
     */
    public static JSONArray registerInterface(MockMvc mockMvc,
                                              InterfaceRegisterDTO interfaceRegisterDTO,
                                              ArrayList<NPService> serviceList) throws Exception {
        String requestBody = JSONObject.toJSONString(interfaceRegisterDTO);


        //添加模拟的service
//        ArrayList<NPService> serviceList = TestDATA.getService();
//        serviceService.insert(serviceList.get(0));
        JSONArray serviceJsonArray = BizUtils.insertService(mockMvc, serviceList,1);

        //得到MvcResult自己验证
        mockMvc.perform(post(TestDATA.registerUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value("0"))
                .andReturn(); //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/

        return serviceJsonArray;
    }



    /**
     * 根据服务id获取服务详情
     * @param mockMvc
     * @param sid
     * @return
     * @throws Exception
     */
    public static NPService getServiceById(MockMvc mockMvc, Integer sid) throws Exception {

        String result = mockMvc.perform(get(TestDATA.serviceUrl + "/" + sid)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        return resultObj.getObject("data", NPService.class);
    }

    /**
     * 获取分页列表通用方法
     * @param mockMvc
     * @param url
     * @param page
     * @return
     * @throws Exception
     */
    public static JSONArray getPageList(MockMvc mockMvc, String url, NPPage page) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                get(url).accept(MediaType.APPLICATION_JSON);
        if(page.getPageSize()<0){
            mockHttpServletRequestBuilder.param("pageSize", "-1");
        }else{
            mockHttpServletRequestBuilder.param("page", String.valueOf(page.getPage()));
            mockHttpServletRequestBuilder.param("pageSize", String.valueOf(page.getPageSize()));
            Map<String, Object> map = page.getParams();
            if(map!=null){
                for(String key : map.keySet()){
                    if(map.get(key)!=null){
                        mockHttpServletRequestBuilder.param(key, String.valueOf(map.get(key)));
                    }
                }
            }
        }


        String result = mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andReturn().getResponse().getContentAsString();

        JSONObject resultObj = JSONObject.parseObject(result);

        assertEquals(resultObj.getString("code"),"0");

        if(page.getPageSize()<0) {
            JSONArray list = resultObj.getJSONArray("data");
            return list;
        }else {
            JSONObject data = resultObj.getJSONObject("data");

            JSONArray pageList = data.getJSONArray("list");
            return pageList;
        }
    }

    /**
     * 获取详情
     * @param mockMvc
     * @param url
     * @return
     * @throws Exception
     */
    public static String  getInfo(MockMvc mockMvc, String url) throws Exception {
        String result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn().getResponse().getContentAsString();

        return result;
    }

    /**
     * 删除元素
     * @param mockMvc
     * @param url
     * @throws Exception
     */
    public static void deleteItem(MockMvc mockMvc, String url) throws Exception {
        mockMvc.perform(delete(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));

    }

    public static void addItem(MockMvc mockMvc, String url, String requestBody) throws Exception {
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();
    }

    public static void updateItem(MockMvc mockMvc, String url, String requestBody) throws Exception {
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();
    }





    /**
     * 模板NPPage
     * @param page
     * @param pageSize
     * @param paramName1
     * @param param1
     * @param paramName2
     * @param param2
     * @return
     */
    public static NPPage gererateNPPage(int page,int pageSize,
                                        String paramName1, Object param1,
                                        String paramName2, Object param2){
        NPPage npPage = new NPPage();
        npPage.setPage(page);
        npPage.setPageSize(pageSize);
        if(param1!=null || param2!=null){
            HashMap<String, Object> map = new HashMap<>();
            if(param1!=null){
                map.put(paramName1, param1);
            }
            if(param2!=null){
                map.put(paramName2, param2);
            }
            npPage.setParams(map);
        }
        return npPage;
    }


    public static void addASuit(MockMvc mockMvc, NPSuit npSuit) throws Exception {
        mockMvc.perform(post(TestDATA.suitUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(npSuit))
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess))
                .andReturn();

        return ;
    }





    public static JSONArray generateRoleList(MockMvc mockMvc, NPSuit npSuit, List<NPBizRole> bizRoleList) throws Exception {

        //模拟 套码
        BizUtils.addASuit(mockMvc, npSuit);

        ArrayList<String> suitCodeList = new ArrayList<>();
        suitCodeList.add(npSuit.getSuitCode());


        //添加角色
        for(NPBizRole npBizRole : bizRoleList){
            npBizRole.setSuitCodeList(suitCodeList);
            BizRoleControllerTest.addBizRole(mockMvc, npBizRole);
        }

        //获取列表
        String keyword = bizRoleList.get(0).getRoleName().replaceAll("\\d","");
        NPPage page = BizUtils.gererateNPPage(1,100,"keyword",keyword,null,null);
        return BizUtils.getPageList(mockMvc, TestDATA.bizRoleUrl, page);

    }




}
