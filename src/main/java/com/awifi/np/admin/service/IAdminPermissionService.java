package com.awifi.np.admin.service;

import java.util.List;

import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminRolePermission;
import com.awifi.np.admin.entity.NPPage;

/**
 * 管理系统菜单接口类
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月10日 下午3:07:28
 * 创建作者：沈叶峰
 * 文件名称：IAdminMenuService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IAdminPermissionService {
	
	/**
	 * 获取所有权限
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @param
	 * @date 2017年1月10日 下午3:11:03
	 */
	public List<NPAdminPermission> getPermissionListAll() throws Exception;
	
	/**
	 * 获取某个角色的权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年1月10日 下午3:12:00
	 */
	public List<NPAdminRolePermission> getPermissionIdListByRoleId(Integer roleId) throws Exception;
	
	/**
	 * 新增权限
	 * @param permission
	 * @return
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:51:49
	 */
	public int addPermisson(NPAdminPermission permission) throws Exception;
	/**
	 * 更新权限
	 * @param permission
	 * @return
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:51:55
	 */
	public int updatePermisson(NPAdminPermission permission) throws Exception;

	public List<NPAdminPermission> getPermissionListByRoleId(Integer roleId) throws Exception;
	
	public List<NPAdminPermission> getMenusListByRoleId(Integer roleId) throws Exception;

	public int deletePermisson(String[] ids) throws Exception;

	public NPAdminPermission getPermissonById(Integer id) throws Exception;

	public List<NPAdminPermission> getPermissionList(NPPage page);
}
