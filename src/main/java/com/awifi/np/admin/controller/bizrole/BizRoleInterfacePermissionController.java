package com.awifi.np.admin.controller.bizrole;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.*;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/21
 * 创建作者：卢朱娜
 * 文件名称：BizRoleInterfacePermissionController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("admin/bizrole")
public class BizRoleInterfacePermissionController extends BaseController{

    @Autowired
    private BizRoleServiceImpl bizRoleService;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;


    @RequestMapping(value = "/{rid}/service/{sid}/interface", method = RequestMethod.GET)
    public JSONObject getInterfacesTree(@PathVariable("rid") Long rid,
                                        @PathVariable("sid") Integer sid){
        NPService npService = null;
        NPBizRole npBizRole = null;

        try {
            npBizRole = bizRoleService.getById(rid);
            if (npBizRole == null) {
                return returnError(Constants.ECodeBadParam, "rid");
            }
            npService = serviceService.getById(sid);

            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            }

            if (StringUtil.isEmpty(npService.getRolePermissionApi())) {
                return returnError("E1020001");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
        try{
            return bizRoleInterfaceService.getRoleInterfaceRelation(rid, npService);
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("E1020002", npService.getServiceCode()+" "+e.getClass().getName());
        }

    }


    @RequestMapping(value = "/{rid}/service/{sid}/interface", method = RequestMethod.PUT)
    public JSONObject bindInterface(@PathVariable("rid") Long rid,
                                    @PathVariable("sid") Integer sid,
                                    @RequestBody List<String> interfaceCodeList){
        NPService npService = null;
        NPBizRole npBizRole = null;
        try {
            npBizRole = bizRoleService.getById(rid);
            if (npBizRole == null) {
                return returnError(Constants.ECodeBadParam, "rid");
            }
            npService = serviceService.getById(sid);

            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            }

            if (StringUtil.isEmpty(npService.getRolePermissionApi())) {
                return returnError("E1020001");
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

        try{
            return bizRoleInterfaceService.bindRoleInterface(rid, npService, interfaceCodeList);

        }catch (Exception e){
            e.printStackTrace();
            return returnError("E1020002", npService.getServiceCode()+" "+e.getClass().getName());
        }


    }


//
//    @RequestMapping(value = "/{rid}/interface", method = RequestMethod.GET)
//    public JSONObject getInterfacesTreeByBizRole(@PathVariable("rid") Long rid){
//
//        try {
//            NPBizRole npBizRole = bizRoleService.getById(rid);
//            if(npBizRole==null){
//                return returnError(Constants.ECodeBadParam, "rid");
//            }
//
//            HashMap<String, Object> aMap = new HashMap<>();
//            List<NPService> serviceService.listOnLineService();
//            RequestDTO requestDTO = new RequestDTO();
//            ArrayList<HttpGet> httpGetArrayList = new ArrayList<>();
//            ArrayList<String> keyList = new ArrayList<>();
//            requestDTO.setUuid(UuidUtil.get32UUID());
//
//            for(NPService npService : serviceService){
//                String serviceCode = npService.getServiceCode();
//                HashMap<String, Object> tmpMap = new HashMap<>();
//                aMap.put(serviceCode, tmpMap);
//
//                tmpMap.put("service", npService);
//
//                List<NPInterface> registeredInterfaceList = interfaceService.listByServiceCode(npService.getServiceCode());
//                tmpMap.put("registeredInterfaceList", registeredInterfaceList);
//
//                //构造请求列表
//                if(StringUtil.isNotEmpty(npService.getRolePermissionApi())){
//                    keyList.add(serviceCode);
//                    HttpGet httpGet = new HttpGet(npService.getRolePermissionApi());
//                    httpGetArrayList.add(httpGet);
//                }else if (registeredInterfaceList.size()>0){//接口没配置
////                    JedisUtil.hset(Constants.REDIS_CALLAPI+requestDTO.getUuid(),
////                            serviceCode, JSONObject.toJSONString(returnError("E1020002")));
//                    return returnError("E1020002", serviceCode);
//                }
//            }
//
//            requestDTO.setMethod("get");
//            requestDTO.setKeyList(keyList);
//            requestDTO.setUrisToGet(httpGetArrayList);
//
//            //批量调用接口
//            PoolHttpClientUtil.concurrentCall(requestDTO);
//
//            //从redis中读取调用结果，并返回
//            Map<String, String> apiResultMap = JedisUtil.hgetAll(Constants.REDIS_CALLAPI+requestDTO.getUuid());
//            for(String serviceCode : keyList ){
//                HashMap<String, Object> tmpmap = (HashMap<String, Object>)aMap.get(serviceCode);
//
//                String apiResult = apiResultMap.get(serviceCode);
//                JSONObject apiJobj = JSONObject.parseObject(apiResult);
//                if(apiJobj.getString("code").equals(Constants.ECodeSuccess)){
//                    tmpmap.put("passedInterfaceList", JSONObject.parseArray(apiJobj.getString("data"), String.class));
//                }else{
//                    tmpmap.put("apiresult", apiJobj.getString("msg"));
//                }
//            }
//
//            //整合做后的结果
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
