package com.awifi.np.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPBizRoleMapper;
import com.awifi.np.admin.dao.NPBizSuitRoleMapper;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPBizSuitRole;
import com.awifi.np.admin.entity.NPBizSuitRoleCriteria;
import com.awifi.np.admin.service.IBizSuitRoleService;
import com.awifi.np.admin.utils.MessageUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午2:40:26
 * 创建作者：沈叶峰
 * 文件名称：BizRoleServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class BizSuitRoleServiceImpl implements IBizSuitRoleService {
	@Autowired
	private NPBizSuitRoleMapper bizSuitRoleMapper;
	@Autowired
	private NPBizRoleMapper bizRoleMapper;
	
	public JSONObject saveBizRoleSuits(Long roleId,  List<NPBizSuitRole> suitIdList) throws Exception {
		if(suitIdList == null || suitIdList.size() < 1){
			return MessageUtil.returnError(Constants.ECodeParam,"suitId");
		}
		if(roleId == null || roleId < 0 ){
			return MessageUtil.returnError(Constants.ECodeParam,"roleId");
		}
		
	   //检查角色是否存在
		NPBizRole bizRole =  bizRoleMapper.selectByPrimaryKey(roleId);
		if(bizRole == null || bizRole.getId() == null){
			return MessageUtil.returnError("E1017001"); //角色不存在
		}
		//先删除套码角色关系
		deleteRoleSuits(roleId);
		
		//再保存关联关系
		insertRoleSuits(roleId,suitIdList);
	
		
		return MessageUtil.returnSuccess();
	}
	
	/**
	 * 删除某个角色绑定的所有套码
	 * @param roleId
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午4:15:31
	 */
	public void deleteRoleSuits(Long roleId) throws Exception{
		NPBizSuitRoleCriteria  criteria = new NPBizSuitRoleCriteria();
		criteria.createCriteria().andRoleIdEqualTo(roleId);
		bizSuitRoleMapper.deleteByExample(criteria);
	}

	/**
	 * 套码角色关联
	 * @author 沈叶峰  
	 * @date 2017年1月18日 下午4:42:52
	 */
	public void insertRoleSuits(Long roleId, List<NPBizSuitRole> suitIdList)
			throws Exception {
		if(roleId == null || roleId < 0 ){
			return ;
		}
		
		if(suitIdList == null || suitIdList.size() < 1){
			return ;
		}
		
		for(NPBizSuitRole suitRole : suitIdList){
			NPBizSuitRole  bizSuitRole = new NPBizSuitRole();
			bizSuitRole.setRoleId(suitRole.getRoleId());
			bizSuitRole.setSuitCode(suitRole.getSuitCode());
			bizSuitRole.setShowLevel(suitRole.getShowLevel());
			bizSuitRoleMapper.insert(bizSuitRole);
		}
		
	}


	@Override
	public List<NPBizSuitRole> listByRoleIds(List<Long> roleIds) throws Exception {
		NPBizSuitRoleCriteria example = new NPBizSuitRoleCriteria();
		example.createCriteria().andRoleIdIn(roleIds);
		return bizSuitRoleMapper.selectByExample(example);
	}


	@Override
	public List<String> listSuiteCodeByRoleId(Long roleId) throws Exception {

		return bizSuitRoleMapper.listSuiteCodeByRoleId(roleId);
	}



}
