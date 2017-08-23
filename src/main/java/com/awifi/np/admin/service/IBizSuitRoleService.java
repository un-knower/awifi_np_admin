package com.awifi.np.admin.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPBizSuitRole;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月18日 下午2:28:03
 * 创建作者：沈叶峰
 * 文件名称：IBizRoleService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IBizSuitRoleService {
	/**
	 * 角色套码绑定
	 * @param roleId
	 * @param suitIds
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午2:39:28
	 */
	public JSONObject saveBizRoleSuits(Long roleId, List<NPBizSuitRole> suitIdList) throws Exception;

	/**
	 * 保存套餐和角色关系
	 * @param roleId
	 * @param suitIdList
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午4:38:18
	 */
	public void insertRoleSuits(Long roleId, List<NPBizSuitRole> suitIdList) throws Exception;


	/**
	 * 删除套码通过roleid
	 * @param roleId
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午4:17:08
	 */
	public void deleteRoleSuits(Long roleId) throws Exception;

	/**
	 * 按roleid顺序列出角色和套码关系
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	List<NPBizSuitRole> listByRoleIds(List<Long> roleIds) throws Exception;

	/**
	 * 获取角色绑定的套码列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<String> listSuiteCodeByRoleId(Long roleId) throws Exception;

}
