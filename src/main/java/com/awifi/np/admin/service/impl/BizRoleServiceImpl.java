package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPBizRoleMapper;
import com.awifi.np.admin.dao.NPBizSuitRoleMapper;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.IBizRoleService;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/13
 * 创建作者：卢朱娜
 * 文件名称：BizRoleServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class BizRoleServiceImpl implements IBizRoleService {

    @Autowired
    private NPBizRoleMapper npBizRoleMapper;
    @Autowired
    private NPBizSuitRoleMapper npBizSuitRoleMapper;
    @Autowired
    private BizSuitRoleServiceImpl bizSuitRoleService;


    public List<Long> listPageBizRoleId(NPPage page, String keyword) throws Exception {
        HashMap<String,Object> params = new HashMap<>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        page.setParams(params);

        return npBizRoleMapper.listPageBizRoleId(page);
    }

    @Override
    public List<NPBizRole> listAll() throws Exception {
        return npBizRoleMapper.selectByExample(null);
    }

    @Override
    public List<NPBizRole> listByIds(List<Long> roleIdList) throws Exception {
        NPBizRoleCriteria example = new NPBizRoleCriteria();
        example.createCriteria().andIdIn(roleIdList);
        example.setOrderByClause("id desc");

        return npBizRoleMapper.selectByExample(example);
    }

    @Override
    public NPBizRole getById(Long roleId) throws Exception {

        return npBizRoleMapper.selectByPrimaryKey(roleId);
    }


    public void bindRoleSuitBulk(Long roleId, List<String> suitCodeList){
        ArrayList<NPBizSuitRole> bizSuitRoleArrayList = new ArrayList<>();
        for(int i = 0;i<suitCodeList.size();i++){
            String suitCode = suitCodeList.get(i);
            NPBizSuitRole npBizSuitRole = new NPBizSuitRole();
            npBizSuitRole.setSuitCode(suitCode);
            npBizSuitRole.setShowLevel(i);
            npBizSuitRole.setRoleId(roleId);
            bizSuitRoleArrayList.add(npBizSuitRole);
        }
        npBizSuitRoleMapper.insertBulk(bizSuitRoleArrayList);
    }

    @Override
    public String addBizRole(NPBizRole npBizRole, NPAdminUser adminUser) throws Exception {
        NPBizRoleCriteria example = new NPBizRoleCriteria();
        example.createCriteria().andRoleNameEqualTo(npBizRole.getRoleName());
        List<NPBizRole> tmproleList = npBizRoleMapper.selectByExample(example);
        if(tmproleList.size()>0){
            return "E1018001";
        }
        npBizRoleMapper.insertSelective(npBizRole);
        List<String> suitCodeList = npBizRole.getSuitCodeList();
        Long roleId = npBizRole.getId();
        bindRoleSuitBulk(roleId, suitCodeList);

        return Constants.ECodeSuccess;
    }

    @Override
    public String updateBizRole(NPBizRole npBizRole) throws Exception {
        NPBizRoleCriteria example = new NPBizRoleCriteria();
        example.createCriteria().andRoleNameEqualTo(npBizRole.getRoleName())
        .andIdNotEqualTo(npBizRole.getId());
        List<NPBizRole> tmproleList = npBizRoleMapper.selectByExample(example);
        if(tmproleList.size()>0){
            return "E1018001";
        }
        npBizRoleMapper.updateByPrimaryKeySelective(npBizRole);

        bizSuitRoleService.deleteRoleSuits(npBizRole.getId());

        List<String> suitCodeList = npBizRole.getSuitCodeList();
        Long roleId = npBizRole.getId();
        bindRoleSuitBulk(roleId, suitCodeList);

        return Constants.ECodeSuccess;
    }

    @Override
    public void deleteById(Long roleId) throws Exception {
        npBizRoleMapper.deleteByPrimaryKey(roleId);
        bizSuitRoleService.deleteRoleSuits(roleId);
    }

    @Override
    public int countByIdList(List<Long> roleIdList) throws Exception {
        NPBizRoleCriteria example = new NPBizRoleCriteria();
        example.createCriteria().andIdIn(roleIdList);

        return npBizRoleMapper.countByExample(example);
    }
}
