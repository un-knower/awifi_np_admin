package com.awifi.np.admin.service;

import java.util.List;

import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.exception.BaseException;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:14:45
* 创建作者：王冬冬
* 文件名称：IAdminUserService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
public interface IAdminUserService {
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:54:42
	 */
	public int addUser(NPAdminUser user) throws Exception;
	/**
	 * 更新用户
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:54:44
	 */
	public int updateUser(NPAdminUser user) throws Exception;
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:54:46
	 */
	public NPAdminUser getUserById(Integer id);
	/**
	 * 分页显示用户
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 王冬冬  
	 * @param keyword 
	 * @param keywordtype 
	 * @date 2017年1月17日 上午10:54:49
	 */
	public List<NPAdminUser> listUsers(NPPage page, String keyword, Integer keywordtype);
	/**
	 * 用户登录
	 * @param loginAccount
	 * @param loginpwd
	 * @return
	 * @throws LoginException
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:54:53
	 */
	public NPAdminUser login(String loginAccount,String loginpwd) throws BaseException;
	/**
	 * 获取错误登录次数
	 * @param loginAccount
	 * @return
	 * @throws LoginException
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:54:57
	 */
	public int deteleByIds(String[] ids) throws Exception;
	public int updatePassword(NPAdminUser user);
}
