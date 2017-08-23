package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPBizUserMapper;
import com.awifi.np.admin.dao.NPBizUserRoleMapper;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.service.IBizUserService;
import com.awifi.np.admin.utils.MD5;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/17
 * 创建作者：卢朱娜
 * 文件名称：BizUserServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class BizUserServiceImpl implements IBizUserService {

    @Autowired
    private NPBizUserMapper npBizUserMapper;
    @Autowired
    private NPBizUserRoleMapper npBizUserRoleMapper;
    @Autowired
    private BizUserRoleServiceImpl bizUserRoleService;

    @Override
    public List<Long> listPageBizUserId(NPPage page, String keyword,
                                        Long provinceId, Long cityId, Long areaId, Long rid) throws Exception {

        HashMap<String,Object> params = new HashMap<>();
        params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
        params.put("provinceId", provinceId!=null?provinceId:null);
        params.put("cityId", cityId!=null?cityId:null);
        params.put("areaId", areaId!=null?areaId:null);
        params.put("rid", rid!=null?rid:null);
        page.setParams(params);

        return npBizUserMapper.listPageBizUserId(page);

    }

    @Override
    public List<NPBizUser> listByIds(List<Long> idList) throws Exception {
        NPBizUserCriteria example = new NPBizUserCriteria();
        example.createCriteria().andIdIn(idList);
        example.setOrderByClause("id desc");
        return npBizUserMapper.selectByExample(example);
    }

    @Override
    public NPBizUser getById(Long id) throws Exception {
        return npBizUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int countByUserName(String userName) throws Exception {
        NPBizUserCriteria example = new NPBizUserCriteria();
        example.createCriteria().andUserNameEqualTo(userName);
        return npBizUserMapper.countByExample(example);

    }


    public void bindRoleBulk(Long userId, List<Long> roleIdList){
        ArrayList<NPBizUserRole> bizUserRoleArrayList = new ArrayList<>();
        for(int i = 0;i<roleIdList.size();i++){
            Long roleId = roleIdList.get(i);
            NPBizUserRole npBizUserRole = new NPBizUserRole();
            npBizUserRole.setUserId(userId);
            npBizUserRole.setOrder(i);
            npBizUserRole.setRoleId(roleId);
            bizUserRoleArrayList.add(npBizUserRole);
        }
        npBizUserRoleMapper.insertBulk(bizUserRoleArrayList);
    }

    public String addBizUser(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception {
        NPBizUserCriteria example = new NPBizUserCriteria();
        example.createCriteria().andUserNameEqualTo(npBizUser.getUserName());
        List<NPBizUser> tmpUserList = npBizUserMapper.selectByExample(example);
        if(tmpUserList.size()>0){
            return "E1019001";
        }
        Date now = new Date();
        npBizUser.setCreateDate(now);
        npBizUser.setUpdateDate(now);
        npBizUser.setPassword(MD5.md5(Constants.DEFAULT_PASSWORD));
        npBizUser.setCreateUserId(adminUser!=null?adminUser.getId():null);
        npBizUserMapper.insertSelective(npBizUser);
        List<Long> roleIdList = npBizUser.getRoleIdList();
        Long userId = npBizUser.getId();
        bindRoleBulk(userId, roleIdList);

        return Constants.ECodeSuccess;
    }




    @Override
    public String updateBizUser(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception {
        NPBizUserCriteria example = new NPBizUserCriteria();
        example.createCriteria().andUserNameEqualTo(npBizUser.getUserName())
                .andIdNotEqualTo(npBizUser.getId());
        List<NPBizUser> tmpUserList = npBizUserMapper.selectByExample(example);
        if(tmpUserList.size()>0){
            return "E1019001";
        }
        npBizUserMapper.updateByPrimaryKeySelective(npBizUser);

        bizUserRoleService.deleteByUserId(npBizUser.getId());

        List<Long> roleIdList = npBizUser.getRoleIdList();
        Long userId = npBizUser.getId();
        bindRoleBulk(userId, roleIdList);

        return Constants.ECodeSuccess;
    }

    @Override
    public void resetPassword(NPBizUser npBizUser, NPAdminUser adminUser) throws Exception {
        NPBizUser newNPBizUser = new NPBizUser();
        newNPBizUser.setId(npBizUser.getId());
        newNPBizUser.setPassword(MD5.md5(Constants.DEFAULT_PASSWORD));
        newNPBizUser.setUpdateDate(new Date());

        npBizUserMapper.updateByPrimaryKeySelective(newNPBizUser);

    }

    @Override
    public void deleteById(Long id) throws Exception {
        bizUserRoleService.deleteByUserId(id);

        npBizUserMapper.deleteByPrimaryKey(id);

    }

}
