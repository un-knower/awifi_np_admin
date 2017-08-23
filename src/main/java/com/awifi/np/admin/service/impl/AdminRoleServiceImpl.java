package com.awifi.np.admin.service.impl;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dao.NPAdminRoleMapper;
import com.awifi.np.admin.dao.NPAdminRolePermissionMapper;
import com.awifi.np.admin.dao.NPAdminUserMapper;
import com.awifi.np.admin.entity.*;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IAdminPermissionService;
import com.awifi.np.admin.service.IAdminRoleService;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 下午2:56:23
* 创建作者：王冬冬
* 文件名称：AdminRoleServiceImpl.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
@Service
public class AdminRoleServiceImpl implements IAdminRoleService{

	@Autowired
	private NPAdminRoleMapper roleMapper;

	@Autowired
	private NPAdminRolePermissionMapper rolePermissonMapper;
	
	@Autowired
	private NPAdminUserMapper userMapper;
	
	@Autowired
	private IAdminPermissionService adminPermissionService;
	
	/**
	 * 新增角色
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:58:42
	 */
	public int addRole(NPAdminRole role) throws Exception{
		roleMapper.insertSelective(role);
		List<NPAdminPermission> permissions=role.getPermissions();
		
		dfsAddPermission(permissions,role);
		return 1;
	}
	/**
	 * 
	 * @param permissions
	 * @param role
	 * @author 王冬冬  
	 * @date 2017年2月23日 上午11:16:46
	 */
	private void dfsAddPermission(List<NPAdminPermission> permissions,NPAdminRole role) {
		if(permissions!=null&&!permissions.isEmpty()){
			for(NPAdminPermission permission:permissions){
					NPAdminRolePermission rolePermission=new NPAdminRolePermission();
					rolePermission.setPermissionId(permission.getId());
					rolePermission.setRoleId(role.getId());
					rolePermission.setCreateDate(new Date());
					rolePermissonMapper.insertSelective(rolePermission);	
					dfsAddPermission(permission.getChildPermission(),role);
			}
		}
	}

	private void dfsUpdatePermission(List<NPAdminPermission> permissions,List<NPAdminRolePermission> rolePermissions,NPAdminRole role) {
		if(permissions!=null&&!permissions.isEmpty()){
			for (NPAdminPermission permission : permissions) {
				NPAdminRolePermission rolePermission = null;
				int index = indexOf(permission, rolePermissions);
				if (index != -1) {//如果之前存在，还原
					rolePermission = rolePermissions.get(index);
				} else {//新增的插入
					rolePermission = new NPAdminRolePermission();
					rolePermission.setPermissionId(permission.getId());
					rolePermission.setRoleId(role.getId());
					rolePermission.setCreateDate(new Date());
				}
				rolePermissonMapper.insertSelective(rolePermission);
				dfsUpdatePermission(permission.getChildPermission(),rolePermissions,role);
			}
		}
	}

	/**
	 * 更新角色
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:58:47
	 */
	public int updateRole(NPAdminRole role) throws Exception{
		role.setUpdateDate(new Date());
		roleMapper.updateByPrimaryKeySelective(role);
		//查询该角色已有的角色权限关系
		NPAdminRolePermissionCriteria criteria=new NPAdminRolePermissionCriteria();
		criteria.createCriteria().andRoleIdEqualTo(role.getId());
		List<NPAdminRolePermission> rolePermissions=rolePermissonMapper.selectByExample(criteria);
		//删除角色权限关系
		rolePermissonMapper.deleteByBatchRoleIds(new String[]{role.getId().toString()});
		List<NPAdminPermission> permissions=role.getPermissions();
		dfsUpdatePermission(permissions,rolePermissions,role);
		removeRolePermissionFromRedis(new String[]{role.getId().toString()});
		return 1;
	}

	/**
	 * 获取某个权限在角色权限列表的下标
	 * @param permission
	 * @param rolePermissions
	 * @return 
	 * @author 王冬冬  
	 * @date 2017年1月22日 下午3:39:03
	 */
	private int indexOf(NPAdminPermission permission, List<NPAdminRolePermission> rolePermissions) {
		for(int i=0;i<rolePermissions.size();i++){
			if(rolePermissions.get(i).getPermissionId().intValue()==permission.getId().intValue()){
				return i;
		   }
		}
		return -1;
	}

	/**
	 * 根据id获取角色
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:58:50
	 */
	public NPAdminRole getRoleById(Integer id) throws Exception {

		NPAdminRole role= roleMapper.selectByPrimaryKey(id);
	    List<NPAdminPermission> permissions=adminPermissionService.getPermissionListByRoleId(id);
		role.setPermissions(permissions);
		return role;
	}

	/**
	 * 分页列表显示角色
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:58:52
	 */
	public List<NPAdminRole> listRoles(NPPage page, String keyword) {
		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("keyword", StringUtil.isNotEmpty(keyword)?"%"+keyword+"%":null);
		page.setParams(params);
		return roleMapper.listPageRoles(page);
	}

	/**
	 * 删除角色
	 * @author 王冬冬  
	 * @date 2017年1月22日 下午5:15:57
	 */
	@Override
	public int deteleByIds(String ids) throws Exception {
		
		 NPAdminUserCriteria criteria=new NPAdminUserCriteria();
		  criteria.createCriteria().andRoleIdEqualTo(Integer.parseInt(ids));
		if(userMapper.countByExample(criteria)!=0L){
			throw new BaseException("角色不能删除");
		}
		String [] idArr=new String[]{ids};
		rolePermissonMapper.deleteByBatchRoleIds(idArr);
		roleMapper.deleteByIds(idArr);
		removeRolePermissionFromRedis(idArr);
		return 1;
	}

	@Override
	public List<NPAdminRole> allRoleList() {
		return roleMapper.selectByExample(null);
	}

	/**
	 * 从redis删除该角色的权限树和菜单树
	 * @param idArr
	 * @author 王冬冬  
	 * @date 2017年1月22日 下午2:40:26
	 */
	private void removeRolePermissionFromRedis(String[] idArr) {	
		for(String id:idArr){
			JedisUtil.del(Constants.PERMISSON_KEY+id);
			JedisUtil.del(Constants.MENU_KEY+id);
		}
	}
}
