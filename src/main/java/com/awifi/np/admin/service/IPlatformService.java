package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/11
 * 创建作者：卢朱娜
 * 文件名称：IPlatformService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IPlatformService {

    List<NPPlatform> listAll() throws Exception;
    NPPlatform getById(Integer id) throws Exception;
    List<NPPlatform> selectByAppId(String appId) throws Exception;
    String update(NPPlatform platform) throws Exception;
    void insert(NPPlatform platform, NPAdminUser user) throws Exception;
    List<NPPlatform> listPagePlatform(NPPage page, String keyword) throws Exception;

}
