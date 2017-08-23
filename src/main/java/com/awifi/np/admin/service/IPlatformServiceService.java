package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformService;
import com.awifi.np.admin.entity.NPService;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/19
 * 创建作者：卢朱娜
 * 文件名称：IPlatformServiceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IPlatformServiceService {

    List<NPService> getServiceList4Bind(NPPlatform npPlatform) throws Exception;
    void insertBind(NPPlatform npPlatform, List<String> serviceCodeList) throws Exception;
    void insert(String appId, String serviceCode, Boolean isOwner) throws Exception;
    List<NPPlatformService> getBindedList(NPPlatform npPlatform) throws Exception;

}
