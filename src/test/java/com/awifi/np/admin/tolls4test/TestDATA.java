package com.awifi.np.admin.tolls4test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dto.InterfaceDTO;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.utils.MD5;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/17
 * 创建作者：卢朱娜
 * 文件名称：TestDATA.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class TestDATA {

    public static String serviceCode4test = "111";
    public static String serviceKey4test = "1234567890";
    public static long timestamp4test = 1484704926118L;
    public static String appIdPre = "appId";
    public static String appId4test = "pingtailzn";
    public static String serviceCodePre = "serviceCode";


    public static String platformUrl = "/admin/platform";
    public static String serviceUrl = "/admin/service";
    public static String registerUrl = "/externalapi/interface";
    public static String publishUrl = "/admin/servicepublishlog";
    public static String interfaceUrl = "/admin/interface";
    public static String suitUrl = "/admin/suit";
    public static String bizRoleUrl = "/admin/bizrole";
    public static String bizUserUrl = "/admin/bizuser";



    /**
     * 模拟业务系统传送的接口内容
     * @return
     */
    public static HashMap<String, InterfaceDTO> getInterfaceMap(){
        HashMap<String,InterfaceDTO> interfaceMap = new HashMap<String, InterfaceDTO>();
        InterfaceDTO tmpDTO = new InterfaceDTO();
        tmpDTO.setPath("/users/{id}");
        tmpDTO.setType("GET");
        tmpDTO.setName("getUser");
        InterfaceDTO tmpDTO2 = new InterfaceDTO();
        tmpDTO2.setPath("/users");
        tmpDTO2.setType("POST");
        tmpDTO2.setName("addUser");
        interfaceMap.put("/users/{id}:GET", tmpDTO);
        interfaceMap.put("/users/:POST", tmpDTO2);
        return interfaceMap;
    }

    /**
     * 模拟一个临时表的接口，包含remark， ifcheck=false
     * @return
     */
    public static NPInterfaceTmp getNPInterfaceTmp(){
        Date now = new Date();
        NPInterfaceTmp interfaceTmp = new NPInterfaceTmp();
        interfaceTmp.setServiceCode("111");
        interfaceTmp.setInterfaceCode("/users/{id}:GET");
        interfaceTmp.setInterfaceUrl("users/{id}");
        interfaceTmp.setInterfaceMethod("GET");
        interfaceTmp.setIfcheck(false);
        interfaceTmp.setRemark("获取用户信息");
        interfaceTmp.setCreateDate(now);
        interfaceTmp.setUpdateDate(now);
        return interfaceTmp;
    }

    /**
     * 模拟一个service
     * @return
     */
    public static ArrayList<NPService> getService(){
        NPService service = new NPService();
        service.setServiceCode(serviceCode4test);
        service.setServiceName(serviceCode4test);
        service.setServiceKey(serviceKey4test);
        service.setPublishStatus(Constants.SERVICR_NOT_ON_LINE);
        ArrayList<NPService> serviceList = new ArrayList<NPService>();
        serviceList.add(service);
        return serviceList;
    }

    /**
     * 模拟一个service列表
     *
     */
    public static ArrayList<NPService> getServiceList(){
        ArrayList<NPService> serviceList = new ArrayList<NPService>();
        for(int i=0; i<6; i++){
            NPService service = new NPService();
            service.setServiceCode(serviceCodePre + i);
            service.setServiceKey("serviceKey" + i);
            service.setServiceName("serviceName" + i);
            service.setPublishStatus((byte)(i%3));
            serviceList.add(service);
        }
        return serviceList;
    }

    /**
     * 模拟平台与服务的绑定记录列表
     */
    public static ArrayList<NPPlatformService> getBindList(){
        ArrayList<NPPlatformService> bindList = new ArrayList<NPPlatformService>();

        for(int i=0;i<3;i++){
            NPPlatformService npPlatformService = new NPPlatformService();
            npPlatformService.setIsOwner(true);
            npPlatformService.setAppId(appId4test);
            npPlatformService.setServiceCode("serviceCode"+i);
            bindList.add(npPlatformService);
        }
        for(int i=3; i<6; i++){
            NPPlatformService npPlatformService = new NPPlatformService();
            npPlatformService.setIsOwner(false);
            npPlatformService.setAppId(appId4test);
            npPlatformService.setServiceCode("serviceCode"+i);
            bindList.add(npPlatformService);
        }

        return bindList;
    }


    /**
     * 模拟一个合法的interfaceRegisterDTO
     * @return
     */
    public static InterfaceRegisterDTO getInterfaceRegisterDTO(){
        InterfaceRegisterDTO interfaceRegisterDTO = new InterfaceRegisterDTO();
        interfaceRegisterDTO.setServiceCode(serviceCode4test);
        interfaceRegisterDTO.setToken(MD5.md5(serviceCode4test+serviceKey4test+timestamp4test));
        interfaceRegisterDTO.setTimestamp(timestamp4test);
        interfaceRegisterDTO.setInterfaceMap(getInterfaceMap());

        return interfaceRegisterDTO;
    }

    /**
     * 模拟连续4个平台
     */
    public static ArrayList<NPPlatform> getNPPlatformList(){
        ArrayList<NPPlatform> platformArrayList = new ArrayList<NPPlatform>();

        for(int i=1; i<5; i++){
            NPPlatform npPlatform = new NPPlatform();
            npPlatform.setAppId(appIdPre+ i);
            npPlatform.setAppKey("appKey"+i);
            npPlatform.setRemark("remark"+i);
            npPlatform.setPlatformName("platformName"+i);
            npPlatform.setPlatformUrl("http://url");
            platformArrayList.add(npPlatform);
        }
        return platformArrayList;
    }

    /**
     * 模拟一个平台数据
     * @return
     */
    public static NPPlatform getNPPlatform(){
        NPPlatform npPlatform = new NPPlatform();
        npPlatform.setAppId(appId4test);
        npPlatform.setAppKey("pingtaiKey");
        npPlatform.setRemark("remark");
        npPlatform.setPlatformName("平台");
        npPlatform.setPlatformUrl("http://url");
        return npPlatform;
    }

    /**
     * 模拟一个平台数据
     * @return
     */
    public static NPPlatform getNPPlatform(String appId){
        NPPlatform npPlatform = getNPPlatform();
        npPlatform.setAppId(appId);
        return npPlatform;
    }

    /**
     * 模拟已绑定的服务列表
     * @return
     */
    public static ArrayList<String> getBindedList(){
        ArrayList<NPService> serviceArrayList = getServiceList();
        ArrayList<String> bindedList = new ArrayList<String>();

        bindedList.add(serviceArrayList.get(0).getServiceCode());
        bindedList.add(serviceArrayList.get(1).getServiceCode());
        bindedList.add(serviceArrayList.get(4).getServiceCode());
        bindedList.add(serviceArrayList.get(5).getServiceCode());

        return bindedList;
    }


    /**
     * 模拟已拥有的服务列表
     * @return
     */
    public static ArrayList<String> getOwnedList(){
        ArrayList<NPService> serviceArrayList = getServiceList();
        ArrayList<String> ownedList = new ArrayList<String>();

        ownedList.add(serviceArrayList.get(0).getServiceCode());
        ownedList.add(serviceArrayList.get(1).getServiceCode());

        return ownedList;
    }



    /**
     * 模拟接口列表
     * @return
     */
    public static ArrayList<NPInterface> getInterfaceList(){
        ArrayList<NPInterface> arrayList = new ArrayList<>();
        for(int i=0;i<5;i++){
            NPInterface npInterface = new NPInterface();
//            npInterface.setServiceCode("111");
//            npInterface.setInterfaceCode("/users/{id}:GET");
            npInterface.setInterfaceUrl("/interfaceUrl"+i);
            npInterface.setInterfaceMethod("GET");
            npInterface.setInterfaceName("interfaceName"+i);
            npInterface.setIfcheck(false);
            npInterface.setRemark("remark"+i);
            arrayList.add(npInterface);
        }
        return arrayList;
    }



    /**
     * 模拟3个角色
     * @return
     */
    public static ArrayList<NPBizRole> getNPRoleList() {
        ArrayList<NPBizRole> roleList = new ArrayList<>();
        for(int i=0;i<3;i++){
            NPBizRole npBizRole = new NPBizRole();
            npBizRole.setRoleName("RoleName"+i);
//            ArrayList<String> suitCodeList = new ArrayList<>();
//            suitCodeList.add("skin4");
//            suitCodeList.add("skin5");
//            npBizRole.setSuitCodeList(suitCodeList);
            roleList.add(npBizRole);
        }

        return roleList;
    }

    /**
     * 模拟一个套码
     * @return
     */
    public static NPSuit getNPSuit(){
        NPSuit npSuit = new NPSuit();
        npSuit.setId(0);
        npSuit.setAppId("111");
        npSuit.setSuitCode("skin0");
        return npSuit;
    }

    public static ArrayList<NPSuit> getNPSuitList(int count){
        ArrayList<NPSuit> suitArrayList = new ArrayList<>();
        for(int i=0;i<count;i++){
            NPSuit npSuit = new NPSuit();
            npSuit.setAppId("111");
            npSuit.setSuitCode("SuitCode"+i);
            suitArrayList.add(npSuit);
        }
        return suitArrayList;
    }


    public static ArrayList<NPBizUser> getNPBizUserList(int count){
        ArrayList<NPBizUser> userArrayList = new ArrayList<>();
        for(int i=0;i<count;i++){
            NPBizUser npBizUser = new NPBizUser();
            npBizUser.setUserName("UserName"+i);
            npBizUser.setContactWay("ContactWay"+i);
            npBizUser.setProvinceId(31L);
            npBizUser.setCityId(383L);
            npBizUser.setAreaId(3232L);
            userArrayList.add(npBizUser);
        }
        return userArrayList;
    }



}
