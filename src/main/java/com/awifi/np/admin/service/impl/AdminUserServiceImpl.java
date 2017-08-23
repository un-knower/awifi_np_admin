																												package com.awifi.np.admin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.awifi.np.admin.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminUserMapper;
import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPAdminRoleCriteria;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPAdminUserCriteria;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IAdminUserService;
import com.awifi.np.admin.utils.MD5;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.util.StringUtils;

																												/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 下午2:48:41
* 创建作者：王冬冬
* 文件名称：AdminUserService.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@Service
public class AdminUserServiceImpl implements IAdminUserService{

	@Autowired
	private NPAdminUserMapper userMapper;
	
	@Autowired
	private NPAdminRoleMapper roleMapper;

	/**
	 * 新增用户
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午11:00:01
	 */
	public int addUser(NPAdminUser user) throws Exception{
		user.setCreateDate(new Date());
		user.setLoginPwd(StringUtils.isEmpty(user.getLoginPwd())?MD5.md5(Constants.DEFAULT_PASSWORD):MD5.md5(user.getLoginPwd()));
		return userMapper.insertSelective(user);
	}

	/**
	 * 更新用户
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午11:00:03
	 */
	public int updateUser(NPAdminUser user) throws Exception{
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 根据id获取用户
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午11:00:06
	 */
	public NPAdminUser getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 分页列表显示用户
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午11:00:08
	 */
	public List<NPAdminUser> listUsers(NPPage page,String keyword,Integer keywordtype) {
		HashMap<String,Object> params = new HashMap<String, Object>();
//		if(keywordtype!=null&&keywordtype.intValue()==1){//搜索词是角色名称
		if(StringUtils.isEmpty(keyword)){
			params.put("keyword", null);
		}else {
			params.put("keyword", StringUtil.isNotEmpty(keyword) ? "%" + keyword + "%" : null);
			NPAdminRoleCriteria criteria = new NPAdminRoleCriteria();
			criteria.createCriteria().andRoleNameLike("%" + keyword + "%");
			List<NPAdminRole> roleList = roleMapper.selectByExample(criteria);
			if (roleList != null && roleList.size() != 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(roleList.get(0).getId());
				for (int i = 1; i < roleList.size(); i++) {
					sb.append("," + roleList.get(i).getId());
				}
				keyword = sb.toString();
				params.put("keyword1", StringUtil.isNotEmpty(keyword) ? keyword : null);

			} else {
				params.put("keyword1",null);
			}
		}
        page.setParams(params);
		return userMapper.listPageUsers(page);
	}

	/**
	 * 用户登录
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午11:00:12
	 */
	public NPAdminUser login(String loginAccount, String loginpwd) throws BaseException {
		NPAdminUserCriteria criteria=new NPAdminUserCriteria();
		criteria.createCriteria().andLoginAccountEqualTo(loginAccount).andLoginPwdEqualTo(loginpwd);
		List<NPAdminUser> users=userMapper.selectByExample(criteria);
		if(users==null||users.size()==0||users.get(0)==null){
			throw new BaseException("用户名或密码错误");
		}
		NPAdminUser user=users.get(0);
		user.setLastLoginDate(new Date());
		userMapper.updateByPrimaryKeySelective(user);//更新登录时间
		return user;
	}

//	/**
//	 * 登录错误次数加1
//	 * @param loginAccount
//	 * @param loginpwd
//	 * @author 王冬冬
//	 * @date 2017年2月16日 下午4:17:34
//	 */
//	@Override
//	public void addErrorTime(String loginAccount) {
//
//		NPAdminUserCriteria criteria=new NPAdminUserCriteria();
//		criteria.createCriteria().andLoginAccountEqualTo(loginAccount);
//		List<NPAdminUser> users=userMapper.selectByExample(criteria);
//		if(users==null||users.size()==0||users.get(0)==null){
//			return;
//
//		}
//		NPAdminUser user=users.get(0);
//		user.setErrorCount(user.getErrorCount()==null?1:user.getErrorCount()+1);
//		userMapper.updateByPrimaryKeySelective(user);
//	}

//	/**
//	 * 登录错误次数
//	 * @author 王冬冬
//	 * @date 2017年1月17日 上午9:30:55
//	 */
//	public int countLoingErrorTimes(String loginAccount) throws BaseException {
//
//	    NPAdminUserCriteria criteria=new NPAdminUserCriteria();
//		criteria.createCriteria().andLoginAccountEqualTo(loginAccount);
//		List<NPAdminUser> users=userMapper.selectByExample(criteria);
//		if(users==null||users.size()==0||users.get(0)==null){
//			throw new BaseException("用户不存在");
//		}
//		return users.get(0).getErrorCount();
//	}

	@Override
	public int deteleByIds(String[] ids) throws Exception {
		userMapper.deleteByIds(ids);
		return 1;
	}

	@Override
	public int updatePassword(NPAdminUser user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

}
