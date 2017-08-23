package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizUserRole;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：IBizUserRoleService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IBizUserRoleService {
    int countByRoleId(Long roleId) throws Exception;
    List<NPBizUserRole> listByUserIds(List<Long> userIdList) throws Exception;
    List<NPBizRole> listRoleByUserId(Long uid) throws Exception;
    void deleteByUserId(Long userId) throws Exception;
}
