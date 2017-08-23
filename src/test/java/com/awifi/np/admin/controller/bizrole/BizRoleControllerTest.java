package com.awifi.np.admin.controller.bizrole;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：BizRoleControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPSuit;
import com.awifi.np.admin.tolls4test.BizUtils;
import com.awifi.np.admin.tolls4test.TestDATA;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.ArrayList;


@RunWith(SpringJUnit4ClassRunner.class)
//使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉;
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class BizRoleControllerTest {
    private String urlprefix = TestDATA.bizRoleUrl;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    public static void addBizRoleError(MockMvc mockMvc, NPBizRole npBizRole, String code) throws Exception {
        String requestBody = JSONObject.toJSONString(npBizRole);
        mockMvc.perform(post(TestDATA.bizRoleUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(code))
                .andReturn();
    }

    public static void addBizRole(MockMvc mockMvc, NPBizRole npBizRole) throws Exception {
        String requestBody = JSONObject.toJSONString(npBizRole);
        BizUtils.addItem(mockMvc, TestDATA.bizRoleUrl,requestBody);
    }


    public static void updateBizRole(MockMvc mockMvc, NPBizRole newNPBizRole) throws Exception {
        String requestBody = JSONObject.toJSONString(newNPBizRole);
        BizUtils.updateItem(mockMvc, TestDATA.bizRoleUrl,requestBody);
    }



    /**
     * 1、模拟几个套码
     * 2、连续创建几个业务角色
     * 3、获取业务列表
     * 4、更新角色
     * 5、获取业务角色详情
     * 6、删除角色
     */
    @Test
    public void bizRoleTest() throws Exception {
        //添加一条记录
        ArrayList<NPSuit> suitArrayList = TestDATA.getNPSuitList(2);
        suitArrayList.get(0).setId(200);
        BizUtils.addASuit(mockMvc, suitArrayList.get(0));
        suitArrayList.get(1).setId(300);
        BizUtils.addASuit(mockMvc, suitArrayList.get(1));

        ArrayList<String> suitCodeList = new ArrayList<>();
        suitCodeList.add(suitArrayList.get(0).getSuitCode());
        suitCodeList.add(suitArrayList.get(1).getSuitCode());

        //连续添加3条
        ArrayList<NPBizRole> bizRoleArrayList = TestDATA.getNPRoleList();
        for(NPBizRole npBizRole : bizRoleArrayList){
            npBizRole.setSuitCodeList(suitCodeList);
            addBizRole(mockMvc, npBizRole);
        }

        //获取列表
        String keyword = bizRoleArrayList.get(0).getRoleName().replaceAll("\\d","");
        NPPage page = BizUtils.gererateNPPage(1,100,"keyword",keyword,null,null);
        JSONArray jsonArray = BizUtils.getPageList(mockMvc, TestDATA.bizRoleUrl, page);
        assertSame(bizRoleArrayList.size(), jsonArray.size());

        NPBizRole npBizRole0 = jsonArray.getObject(0, NPBizRole.class);
        NPBizRole npBizRole1 = jsonArray.getObject(1, NPBizRole.class);

        //更改角色
        npBizRole0.setRoleName("RoleName9");
        ArrayList<String> suitCodeList2 = new ArrayList<>();
        suitCodeList2.add(suitArrayList.get(0).getSuitCode());
        npBizRole0.setSuitCodeList(suitCodeList2);
        updateBizRole(mockMvc, npBizRole0);


        //获取详情
        String result = BizUtils.getInfo(mockMvc, urlprefix+"/"+npBizRole0.getId());
        JSONObject jsonObject = JSONObject.parseObject(result);
        NPBizRole newNPBizRole0 = JSONObject.parseObject(jsonObject.getString("data"), NPBizRole.class);
        assertEquals(npBizRole0.getRoleName(), newNPBizRole0.getRoleName());
        assertSame(npBizRole0.getSuitCodeList().size(), newNPBizRole0.getSuitCodeList().size());


        //删除角色
        BizUtils.deleteItem(mockMvc, urlprefix+"/"+npBizRole0.getId());


        //获取列表
        page = BizUtils.gererateNPPage(1,100,"keyword",keyword,null,null);
        jsonArray = BizUtils.getPageList(mockMvc, TestDATA.bizRoleUrl, page);
        assertSame(bizRoleArrayList.size()-1, jsonArray.size());


        //add参数为空
        NPBizRole tmprole = new NPBizRole();
        addBizRoleError(mockMvc, tmprole, Constants.ECodeParam);
        tmprole.setRoleName(npBizRole1.getRoleName());
        addBizRoleError(mockMvc, tmprole, Constants.ECodeParam);

        //角色名称重复
        tmprole.setSuitCodeList(suitCodeList);
        addBizRoleError(mockMvc, tmprole, "E1018001");


    }

    /**
     * 列表没有符合条件的角色
     */
    @Test
    public void testNone() throws Exception {
        String keyword = "bukenengnnnnnnnnnnnnnnnnnnnnnnnnnn";
        NPPage page = BizUtils.gererateNPPage(1,100,"keyword",keyword,null,null);
        JSONArray jsonArray = BizUtils.getPageList(mockMvc, TestDATA.bizRoleUrl, page);
        assertSame(null, jsonArray);

    }

    /**
     * 获取列表所有信息
     * @throws Exception
     */
    @Test
    public void testAll() throws Exception {
        NPPage page = BizUtils.gererateNPPage(0,-1,null,null,null,null);
        BizUtils.getPageList(mockMvc, TestDATA.bizRoleUrl, page);
    }

    /**
     * 获取一个id不存在的用户
     * @throws Exception
     */
    @Test
    public void testget0() throws Exception {
        mockMvc.perform(get(urlprefix+"/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8")) //验证响应contentType
                .andExpect(jsonPath("$.code").value(Constants.ECodeBadParam))
                .andReturn().getResponse().getContentAsString();
    }

}
