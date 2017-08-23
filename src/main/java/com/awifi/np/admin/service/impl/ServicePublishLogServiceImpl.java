package com.awifi.np.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPServicePublishLogMapper;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.entity.NPServicePublishLog;
import com.awifi.np.admin.entity.NPServicePublishLogCriteria;
import com.awifi.np.admin.service.IServicePublishLogService;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/8
 * 创建作者：卢朱娜
 * 文件名称：ServicePublishLogServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class ServicePublishLogServiceImpl implements IServicePublishLogService{

    @Autowired
    private NPServicePublishLogMapper publishLogMapper;
    @Autowired
    private ServiceServiceImpl  serviceService;
    @Autowired
    private InterfaceTmpServiceImpl interfaceTmpService;
    @Autowired
    private InterfaceServiceImpl interfaceService;

    @Override
    public NPServicePublishLog getJoinInfo(Integer id) throws Exception {

        return publishLogMapper.getJoinInfo(id);
    }

    @Override
    public String update(NPServicePublishLog npServicePublishLog, NPService serviceConfig, NPAdminUser adminUser) throws Exception {
        Date now = new Date();
        byte status = npServicePublishLog.getPublishStatus().byteValue();
        //更新配置
        if(status == Constants.PS_TO_BE_CHECK ){//更新配置
            npServicePublishLog.setConfigDate(now);
            npServicePublishLog.setConfigUserId(adminUser==null?null:adminUser.getId());
            npServicePublishLog.setTestDate(now);
            npServicePublishLog.setTestUserId(adminUser==null?null:adminUser.getId());
            npServicePublishLog.setConfig(JSONObject.toJSONString(serviceConfig));
            publishLogMapper.updateByPrimaryKeySelective(npServicePublishLog);

        }else if(status == Constants.PS_TO_BE_PUBLISH) {//审核服务
            npServicePublishLog.setCheckDate(now);
            npServicePublishLog.setCheckUserId(adminUser==null?null:adminUser.getId());
            publishLogMapper.updateByPrimaryKeySelective(npServicePublishLog);
        }else if(status == Constants.PS_CANCEL){//取消上线
            Integer sid = npServicePublishLog.getServiceId();
            NPService npService = serviceService.getById(sid);
            if(npService==null){
                return "E1014006";
            }
            npServicePublishLog.setCancelDate(now);
            npServicePublishLog.setCancelUserId(adminUser==null?null:adminUser.getId());
            publishLogMapper.updateByPrimaryKeySelective(npServicePublishLog);

            //取消上线, 更改服务的状态
            if(npService.getPublishStatus()==Constants.SERVICR_PUBLISH_ING){
                serviceService.updateStatus(sid, Constants.SERVICR_NOT_ON_LINE);
            }else if(npService.getPublishStatus()==Constants.SERVICR_UPDATE_ING){
                serviceService.updateStatus(sid, Constants.SERVICR_ON_LINE);
            }
            //清除临时接口
            interfaceTmpService.deleteByServiceCode(npService.getServiceCode());

        }else if(status == Constants.PS_PUBLISHED){//服务上线
            //服务上线的话，需要完整的日志记录
            Integer sid = npServicePublishLog.getServiceId();
            NPService npService = serviceService.getById(sid);
            if(npService==null){
                return "E1014006";
            }
            npServicePublishLog.setPublishDate(now);
            npServicePublishLog.setPublishUserId(adminUser==null?null:adminUser.getId());
            publishLogMapper.updateByPrimaryKeySelective(npServicePublishLog);

            //覆盖服务配置
            overwriteConfig(npService, npServicePublishLog.getId());

            //覆盖接口配置
            overwriteInterface(npService.getServiceCode());


            //服务上线, 更改服务的状态
            serviceService.updateStatus(sid, Constants.SERVICR_ON_LINE);

            //清除临时接口
            interfaceTmpService.deleteByServiceCode(npService.getServiceCode());
            Set<String> keys=JedisUtil.keys(Constants.INTERFACE+npServicePublishLog.getServiceCode()+"_*");
            keys.forEach((key)->{
                JedisUtil.del(key);
            });
        }


        return Constants.ECodeSuccess;
    }


    /**
     * 插入新的上线记录，如果服务的状态为待上线，或者已上线，则更改服务状态
     * @param npServicePublishLog
     * @param npService0
     * @param user
     * @throws Exception
     */
    @Override
    public void insert(NPServicePublishLog npServicePublishLog, NPService npService0, NPAdminUser user) throws Exception {
        publishLogMapper.insertSelective(npServicePublishLog);
        Byte status = npService0.getPublishStatus();
        if(status==null || status.byteValue()==Constants.SERVICR_NOT_ON_LINE){
            serviceService.updateStatus(npService0.getId(), Constants.SERVICR_PUBLISH_ING);
        }else if(status.byteValue()==Constants.SERVICR_ON_LINE){
            serviceService.updateStatus(npService0.getId(), Constants.SERVICR_UPDATE_ING);
        }

    }

    public List<NPServicePublishLog> listPagePublishLog(NPPage page, String keyword, Integer publishStatus, Integer sid) throws Exception {
        HashMap<String,Object> params = new HashMap<>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        params.put("publishStatus", publishStatus!=null?publishStatus:null);
        params.put("sid", sid!=null?sid:null);
        page.setParams(params);

        return publishLogMapper.listPagePublishLog(page);
    }


    public static ArrayList<Byte> getOverStaus(){
        ArrayList<Byte> overStatusList = new ArrayList<>();
        overStatusList.add(new Byte(Constants.PS_CANCEL));
        overStatusList.add(new Byte(Constants.PS_PUBLISHED));
        return overStatusList;
    }

    public int countINGByServiceId(Integer sid) throws Exception {
        NPServicePublishLogCriteria example = new NPServicePublishLogCriteria();
        example.createCriteria().andPublishStatusNotIn(getOverStaus()).andServiceIdEqualTo(sid);
        return publishLogMapper.countByExample(example);

    }

    /**
     * 覆盖服务配置
     * @param npService
     * @param
     */
    public void overwriteConfig(NPService npService, Integer logid) throws Exception {
        NPServicePublishLog npServicePublishLog = getJoinInfo(logid);
        NPService serviceConfig = JSONObject.parseObject(npServicePublishLog.getConfig(), NPService.class);

        NPService newNPService = new NPService();
        newNPService.setId(npService.getId());
        newNPService.setServiceHost(serviceConfig.getServiceHost());
        newNPService.setServicePort(serviceConfig.getServicePort());
        newNPService.setVersion(serviceConfig.getVersion());
        newNPService.setCheckAuth(serviceConfig.getCheckAuth());
        newNPService.setCheckSafe(serviceConfig.getCheckSafe());
        newNPService.setRolePermissionApi(serviceConfig.getRolePermissionApi());
        newNPService.setIsMenu(serviceConfig.getIsMenu());
        newNPService.setMenuTreeApi(serviceConfig.getMenuTreeApi());
        newNPService.setRoleMenuApi(serviceConfig.getRoleMenuApi());
        serviceService.updateById(newNPService);
    }


    public void overwriteInterface(String serviceCode) throws Exception {
       interfaceService.deleteByServiceCode(serviceCode);
       interfaceService.insertFromTmp(serviceCode);
    }


}
