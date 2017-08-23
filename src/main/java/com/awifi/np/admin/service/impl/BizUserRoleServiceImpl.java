package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.dao.NPBizUserRoleMapper;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizUserRole;
import com.awifi.np.admin.entity.NPBizUserRoleCriteria;
import com.awifi.np.admin.service.IBizUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：BizUserRoleServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class BizUserRoleServiceImpl implements IBizUserRoleService {

    @Autowired
    private NPBizUserRoleMapper npBizUserRoleMapper;

    @Override
    public int countByRoleId(Long roleId) throws Exception {
        NPBizUserRoleCriteria example = new NPBizUserRoleCriteria();
        example.createCriteria().andRoleIdEqualTo(roleId);
        return npBizUserRoleMapper.countByExample(example);
    }

    @Override
    public List<NPBizUserRole> listByUserIds(List<Long> userIdList) throws Exception {

        return npBizUserRoleMapper.selectByUserIdList(userIdList);
    }

    @Override
    public List<NPBizRole> listRoleByUserId(Long uid) throws Exception {

        return npBizUserRoleMapper.selectByUserId(uid);
    }

    @Override
    public void deleteByUserId(Long userId) throws Exception {
        NPBizUserRoleCriteria example = new NPBizUserRoleCriteria();
        example.createCriteria().andUserIdEqualTo(userId);
        npBizUserRoleMapper.deleteByExample(example);
    }
}
