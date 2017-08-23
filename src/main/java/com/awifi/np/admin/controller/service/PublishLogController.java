package com.awifi.np.admin.controller.service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.impl.ServicePublishLogServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.NPAdminUtil;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/7
 * 创建作者：卢朱娜
 * 文件名称：PublishLogController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/servicepublishlog")
public class PublishLogController extends BaseController{


    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private ServicePublishLogServiceImpl publishLogService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject listServicePublishLog(NPPage page,
                                            @RequestParam(value = "sid", required = false) Integer sid,
                                            @RequestParam(value = "keyword", required = false) String keyword,
                                            @RequestParam(value = "publishStatus", required = false)Integer publishStatus){
        try {
            List<NPServicePublishLog> list = publishLogService.listPagePublishLog(page,keyword,publishStatus,sid);
            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", list);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            retMap.put("publishStatus", publishStatus);
            retMap.put("sid", sid);
            return returnSuccess(retMap);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }

    @RequestMapping(value = "/{logid}", method = RequestMethod.GET)
    public JSONObject getPublishLog(@PathVariable("logid") Integer logid){

        try {
            NPServicePublishLog publishLog = publishLogService.getJoinInfo(logid);
            if (publishLog == null) {
                return returnError(Constants.ECodeBadParam, "logid");
            } else {
                if(StringUtil.isNotEmpty(publishLog.getConfig())){
                    publishLog.setNpService(JSONObject.parseObject(publishLog.getConfig(), NPService.class));
                }
                return returnSuccess(publishLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }


    private JSONObject checkParam(NPService npService){
        if(StringUtil.isEmpty(npService.getServiceHost())){
            return returnError(Constants.ECodeParam, "npService.serviceHost");
        }
        if(npService.getIsMenu()==null){
            return returnError(Constants.ECodeParam, "npService.isMenu");
        }
        if(StringUtil.isEmpty(npService.getCheckAuth())){
            return returnError(Constants.ECodeParam, "npService.checkAuth");
        }
        if(StringUtil.isEmpty(npService.getCheckSafe())){
            return returnError(Constants.ECodeParam, "npService.checkSafe");
        }
        if(npService.getIsMenu()){
            if(StringUtil.isEmpty(npService.getMenuTreeApi())){
                return returnError(Constants.ECodeParam, "npService.menuTreeAPi");
            }
            if(StringUtil.isEmpty(npService.getRoleMenuApi())){
                return returnError(Constants.ECodeParam, "npService.roleMenuApi");
            }
        }
        //判断权限接口和安全接口能否访问
        try {

            if (!NPAdminUtil.pingCheckSafe(npService)) {
                return returnError("E1014002");
            }
        }catch (Exception e){
            return returnError("E1014002");
        }

        try {
            if (!NPAdminUtil.pingCheckAuth(npService)) {
                return returnError("E1014001");
            }

        }catch (Exception e){
            return returnError("E1014001");
        }

        return null;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject addPublishLog(@RequestBody NPServicePublishLog publishLog, HttpServletRequest request){
        NPAdminUser adminUser = SessionUtil.getSessionUser(request);
        NPService npService = publishLog.getNpService();
        if(npService==null){
            return returnError(Constants.ECodeParam, "npService");
        }

        if(npService.getId()==null){
            return returnError(Constants.ECodeParam, "npService.id");
        }

        JSONObject tmpObj = checkParam(npService);
        if(tmpObj!=null){
            return tmpObj;
        }

        try {
            NPService npService0 = serviceService.getById(npService.getId());
            if (npService0 == null) {
                return returnError(Constants.ECodeBadParam, "id");
            }else{
                npService.setServiceCode(npService0.getServiceCode());
            }
            byte serviceStatus0 = npService0.getPublishStatus().byteValue();
            if(serviceStatus0!=Constants.SERVICR_NOT_ON_LINE && serviceStatus0!=Constants.SERVICR_ON_LINE){
                return returnError("E1014003");
            }
            int count = publishLogService.countINGByServiceId(npService.getId());
            if(count>0){
                return returnError("E1014003");
            }
            Date now = new Date();
            NPServicePublishLog newPublishLog = new NPServicePublishLog();
            newPublishLog.setServiceCode(npService0.getServiceCode());
            newPublishLog.setServiceId(npService0.getId());
            newPublishLog.setPublishVersion(npService.getVersion());
            newPublishLog.setConfig(JSONObject.toJSONString(npService));
            newPublishLog.setConfigDate(now);
            newPublishLog.setConfigUserId(adminUser!=null?adminUser.getId():null);
            newPublishLog.setTestDate(now);
            newPublishLog.setTestUserId(adminUser!=null?adminUser.getId():null);
            newPublishLog.setPublishStatus(Constants.PS_TO_BE_CHECK);

            publishLogService.insert(newPublishLog, npService0, adminUser);

            return returnSuccess(newPublishLog);

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONObject updatePublishLog(@RequestBody NPServicePublishLog publishLog, HttpServletRequest request){
        NPAdminUser adminUser = SessionUtil.getSessionUser(request);

        NPService serviceConfig;
        if(publishLog.getId()==null){
            return returnError(Constants.ECodeParam, "id");
        }
        Byte statusByte = publishLog.getPublishStatus();
        if(statusByte==null){
            return returnError(Constants.ECodeParam, "publishStatus");
        }
        byte status = statusByte.byteValue();

        if(status!=Constants.PS_TO_BE_CHECK && status!=Constants.PS_TO_BE_PUBLISH
                && status!=Constants.PS_CANCEL && status!=Constants.PS_PUBLISHED){
            return returnError(Constants.ECodeBadParam, "publishStatus");
        }
        serviceConfig = publishLog.getNpService();
        if(status==Constants.PS_TO_BE_CHECK && serviceConfig==null){
            return returnError(Constants.ECodeBadParam, "npService");
        }
        if(serviceConfig!=null){
            JSONObject tmpobj = checkParam(serviceConfig);
            if(tmpobj!=null){
                return tmpobj;
            }
        }

        try {

            NPServicePublishLog tmppPublishLog = publishLogService.getJoinInfo(publishLog.getId());
            if(tmppPublishLog==null){
                return returnError(Constants.ECodeBadParam, "id");
            }
            publishLog.setServiceId(tmppPublishLog.getServiceId());
            byte tmpstatus = tmppPublishLog.getPublishStatus().byteValue();
            //已完结的服务不能更新
            if(tmpstatus==Constants.PS_CANCEL || tmpstatus==Constants.PS_PUBLISHED){
                return returnError("E1014004");
            }
            //只有已审核的服务才能发布
            if(publishLog.getPublishStatus().equals(new Byte(Constants.PS_PUBLISHED))
                    && tmpstatus!=Constants.PS_TO_BE_PUBLISH){
                return returnError("E1014005");
            }

            String code = publishLogService.update(publishLog, serviceConfig, adminUser);
            if(code.equals(Constants.ECodeSuccess)){
                return returnSuccess();
            }else{
                return returnError(code);
            }

        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


}
