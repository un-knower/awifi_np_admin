package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPBizUser;
import com.awifi.np.admin.entity.NPPage;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/17
 * 创建作者：卢朱娜
 * 文件名称：IBizUserService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IBizUserService {

    List<Long> listPageBizUserId(NPPage page, String keyword, Long proviceId, Long cityId, Long areaId, Long rid) throws Exception;
    List<NPBizUser> listByIds(List<Long> idList) throws Exception;
    NPBizUser getById(Long id) throws Exception;
    int countByUserName(String userName) throws Exception;
    String addBizUser(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception;
    String updateBizUser(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception;
    void resetPassword(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception;
    void deleteById(Long id) throws Exception;



}
