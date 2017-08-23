package com.awifi.np.admin.controller.bizrole;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.tolls4test.BizUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;



/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/22
 * 创建作者：卢朱娜
 * 文件名称：InterfacePermissionControllerTest.java
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
public class InterfacePermissionControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    /**
     * 使用环境中已有的服务和角色
     * 获取权限树
     *
     */
    @Test
    public void testInterfacePermission() throws Exception {
        NPService npService = BizUtils.getServiceById(mockMvc, 6);//项目服务
        Long rid = 1L;
        String apicode=null;

        //获取项目服务的接口权限列表
        JSONObject getJobj = bizRoleInterfaceService.getRoleInterfaceRelation(rid, npService);

        JSONObject.toJSON(getJobj);

        apicode = getJobj.getString("code");

        if(apicode.equals(Constants.ECodeSuccess)){

            JSONArray npInterfaceList = getJobj.getJSONArray("data");
            ArrayList<String> interfaceCodeList = new ArrayList<>();
            for(int i=0;i<npInterfaceList.size();i++){
                NPInterface npInterface = npInterfaceList.getObject(i, NPInterface.class);
                interfaceCodeList.add(npInterface.getInterfaceCode());
            }

            //更新项目服务的角色与接口的权限列表绑定
            JSONObject bindJobj = bizRoleInterfaceService.bindRoleInterface(rid, npService, interfaceCodeList);

            JSONObject.toJSONString(bindJobj);


        }else if(apicode.equals("E1020001")){

        }else if(apicode.equals("E1020002")){

        }else{
            assertSame(0,1);
        }


        //获取项目服务的菜单树
        JSONObject menutreeJobj = bizRoleInterfaceService.getMeuTree(npService);

        JSONObject.toJSON(menutreeJobj);

        apicode = menutreeJobj.getString("code");

        assertEquals(apicode, Constants.ECodeSuccess);


        //获取项目服务于超管的菜单树
        JSONObject menuTreeOfBizRoleJobj = bizRoleInterfaceService.getMenuTreeOfBizRole(rid, npService);

        JSONObject.toJSON(menuTreeOfBizRoleJobj);

        apicode = menuTreeOfBizRoleJobj.getString("code");

        if(apicode.equals(Constants.ECodeSuccess)){

            ArrayList<Long> menuIdList = new ArrayList();
            JSONArray oneList = menuTreeOfBizRoleJobj.getJSONArray("data");
            for(int i=0;i<oneList.size();i++){
                JSONObject menuMapJobj = oneList.getJSONObject(i);
                menuIdList.add(menuMapJobj.getLong("id"));
                JSONArray subList = menuMapJobj.getJSONArray("subMenus");

                if(subList==null) continue;

                for(int j=0;j<subList.size();j++){
                    JSONObject submenu = subList.getJSONObject(j);
                    menuIdList.add(submenu.getLong("id"));
                }
            }

            //重新绑定超管项目服务下的菜单
            JSONObject bindJobj = bizRoleInterfaceService.bindRoleMenu(rid, npService, menuIdList);

        }else{

        }


    }

}
