package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.dao.NPPlatformServiceMapper;
import com.awifi.np.admin.dao.NPServiceMapper;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformService;
import com.awifi.np.admin.entity.NPPlatformServiceCriteria;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.IPlatformServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/19
 * 创建作者：卢朱娜
 * 文件名称：PlatformServiceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class PlatformServiceServiceImpl implements IPlatformServiceService{
    @Autowired
    private NPServiceMapper serviceMapper;
    @Autowired
    private NPPlatformServiceMapper platformServiceMapper;


    public List<NPService> getServiceList4Bind(NPPlatform npPlatform) throws Exception {

        List<NPService> serviceList = serviceMapper.selectByExample(null);
        if(serviceList.size()==0){
            return serviceList;
        }
        List<String> bindedList = platformServiceMapper.selectBindedService(npPlatform.getAppId());
        List<String> ownedList = platformServiceMapper.selectOwnedService(npPlatform.getAppId());

        for(NPService npService : serviceList){
            npService.setBinded(bindedList.indexOf(npService.getServiceCode())>=0);
            npService.setOwned(ownedList.indexOf(npService.getServiceCode())>=0);
        }

        return serviceList;

    }


    public List<NPPlatformService> getBindedList(NPPlatform npPlatform){
        return platformServiceMapper.selectBindedServiceInfo(npPlatform.getAppId());
    }


    public void insertBind(NPPlatform npPlatform, List<String> serviceCodeList) throws Exception {
        String appId = npPlatform.getAppId();
        List<String> ownedList = platformServiceMapper.selectOwnedService(appId);

        ArrayList<NPPlatformService> bindList = new ArrayList<NPPlatformService>();
        for(int i=0;i<serviceCodeList.size();i++){
            String serviceCode = serviceCodeList.get(i);
            NPPlatformService npPlatformService = new NPPlatformService();
            npPlatformService.setAppId(appId);
            npPlatformService.setServiceCode(serviceCode);
            npPlatformService.setIsOwner(ownedList.indexOf(serviceCode)>=0);
            npPlatformService.setListOrder(i);

            bindList.add(npPlatformService);
        }

        NPPlatformServiceCriteria example = new NPPlatformServiceCriteria();
        example.createCriteria().andAppIdEqualTo(appId);
        platformServiceMapper.deleteByExample(example);
        if(bindList.size()>0) {
            platformServiceMapper.insertBulk(bindList);
        }

    }

    /**
     * 先删除绑定所属关系，再添加新的所属关系
     * @param appId
     * @param serviceCode
     * @param isOwner
     */
    public void insert(String appId, String serviceCode, Boolean isOwner){
        NPPlatformServiceCriteria example = new NPPlatformServiceCriteria();
        example.createCriteria().andServiceCodeEqualTo(serviceCode).andIsOwnerEqualTo(isOwner);
        platformServiceMapper.deleteByExample(example);

        NPPlatformService npPlatformService = new NPPlatformService();
        npPlatformService.setServiceCode(serviceCode);
        npPlatformService.setAppId(appId);
        npPlatformService.setIsOwner(isOwner);
        platformServiceMapper.insertSelective(npPlatformService);

    }


}
