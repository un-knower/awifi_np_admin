package com.awifi.np.admin.service;

import java.util.List;

import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPPage;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:14:08
* 创建作者：王冬冬
* 文件名称：IAdminRoleService.java
* 版本：  v1.0
* 功能：角色管理
* 修改记录：
*/
public interface IAdminRoleService {

	/**
	 * 新增角色
	 * @param role
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:53:04
	 */
	public int addRole(NPAdminRole role) throws Exception;
	/**
	 * 更新角色
	 * @param role
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:53:06
	 */
	public int updateRole(NPAdminRole role) throws Exception;
	/**
	 * @param id
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:53:08
	 */
	public NPAdminRole getRoleById(Integer id) throws Exception;
	/**
	 * 列表显示接口
	 * @param pageSize
	 * @param page
	 * @param keyword
     * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:53:12
	 */
	public List<NPAdminRole> listRoles(NPPage page, String keyword);
	public int deteleByIds(String ids) throws Exception;

    List<NPAdminRole> allRoleList();
}
