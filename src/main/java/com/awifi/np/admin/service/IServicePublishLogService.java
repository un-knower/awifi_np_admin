package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.*;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/8
 * 创建作者：卢朱娜
 * 文件名称：IServicePublishLogService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IServicePublishLogService {
    NPServicePublishLog getJoinInfo(Integer id) throws Exception;
    String update(NPServicePublishLog npServicePublishLog, NPService npService, NPAdminUser adminUser) throws Exception;
    void insert(NPServicePublishLog npServicePublishLog, NPService npService0, NPAdminUser user) throws Exception;
    List<NPServicePublishLog> listPagePublishLog(NPPage page, String keyword, Integer status,Integer sid) throws Exception;
    int countINGByServiceId(Integer sid) throws Exception;
}
