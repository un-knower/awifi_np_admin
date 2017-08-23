package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：IServiceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IServiceService {

    List<NPService> getByServiceCode(String serviceCode) throws Exception;
    void insert(NPService service) throws Exception;
//    List<NPService> list() throws Exception;
    List<NPService> listPageService(NPPage page, String keyword) throws Exception;
    List<NPService> listAll() throws Exception;
    List<NPService> listPageLevelOneMenu(NPPage page, String keyword, Boolean isMenu) throws Exception;
    NPService getJoinInfo(int sid);
    void insert(NPService npService, NPPlatform npPlatform, NPAdminUser adminUser);
    String updateAndBindPlatform(NPService npService, NPPlatform npPlatform);
    void updateById(NPService npService);
    void updateStatus(Integer sid, byte status);
    List<NPService> listOnlineService(Boolean isMenu) throws Exception;
    public List<NPService> getByServiceCodes(List<String> servcieCodes) throws Exception;
    public List<NPService> getByRoleId(Long rid) throws Exception;
}
