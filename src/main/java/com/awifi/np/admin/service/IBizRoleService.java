package com.awifi.np.admin.service;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPPage;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：IBizRoleService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IBizRoleService {
    /**
     * 分页查询角色名称或者套码包含keyword的角色id列表
     * @param page
     * @param keyword
     * @return
     * @throws Exception
     */
    List<java.lang.Long> listPageBizRoleId(NPPage page, String keyword) throws Exception;

    /**
     * 获取所有的角色
     * @return
     * @throws Exception
     */
    List<NPBizRole> listAll() throws Exception;

    /**
     * 获取roleIdList中的角色详情
     * @param roleIdList
     * @return
     * @throws Exception
     */
    List<NPBizRole> listByIds(List<Long> roleIdList) throws Exception;

    /**
     * 获取roleId对应的角色
     * @param roleId
     * @return
     * @throws Exception
     */
    NPBizRole getById(Long roleId) throws Exception;

    /**
     * 在一个事务中，判断角色名称是否重复，并添加角色，并且批量添加角色和套码关系
     * @param npBizRole
     * @param adminUser
     * @return
     * @throws Exception
     */
    String addBizRole(NPBizRole npBizRole, NPAdminUser adminUser) throws Exception;

    /**
     * 在一个事务中，判断角色名称是否重复，更新角色信息，批量删除关联的套码，批量添加新的套码绑定
     * @param npBizRole
     * @return
     * @throws Exception
     */
    String updateBizRole(NPBizRole npBizRole) throws Exception;

    /**
     * 在一个事务中，物理删除改角色，以及其绑定的套码
     * @param roleId
     * @throws Exception
     */
    void deleteById(Long roleId) throws Exception;

    /**
     * 校验roleIdList中的id是否都存在
     * @param roleIdList
     * @return
     * @throws Exception
     */
    int countByIdList(List<Long> roleIdList) throws Exception;


}
