package com.awifi.np.admin.controller.permission;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.IAdminPermissionService;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:09:09
* 创建作者：王冬冬
* 文件名称：Permissioncontroller.java
* 版本：  v1.0
* 功能：权限管理
* 修改记录：
*/
@RequestMapping("/admin")
@Controller
public class PermissionController extends BaseController{
  
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAdminPermissionService permissionService;
	
	/**
	 * 新增权限
	 * @param permission
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:01
	 */
	@RequestMapping(value="/permission",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addPermisson(@RequestBody NPAdminPermission permission,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			if(permission==null){
				return returnError("E1003002");
			}
			permissionService.addPermisson(permission);
		} catch (Exception e) {
			JedisUtil.del(Constants.PERMISSONS_NODES);//数据库事务异常时，同时把redis清空，避免数据不同步
			JedisUtil.del(Constants.PERMISSONS_KEY);
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003004");
		}
		return returnSuccess();
	}

	/**
	 * 更新权限
	 * @param permission
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:21
	 */
	@RequestMapping(value="/permission",method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject updatePermisson(@RequestBody NPAdminPermission permission,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			if(permission==null){
				return returnError("E1003002");
			}
			permissionService.updatePermisson(permission);
		} catch (Exception e) {
			JedisUtil.del(Constants.PERMISSONS_NODES);//数据库事务异常时，同时把redis清空，避免数据不同步
			JedisUtil.del(Constants.PERMISSONS_KEY);
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003005");
		}
		return returnSuccess();
	}
	
	/**
	 * 根据id获取权限
	 * @param permission
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:21
	 */
	@RequestMapping(value="/permission/{id}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getPermissonById(@PathVariable("id") Integer id,HttpServletRequest request,HttpServletResponse response) {
		NPAdminPermission p=null;
		try {
			if(id==null){
				return returnError("E1003006");
			}
			p=permissionService.getPermissonById(id);
		} catch (Exception e) {
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
		}
		return returnSuccess(p);
	}
	
	/**
	 * 删除权限
	 * @param permission
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:40
	 */
	@RequestMapping(value="/permission",method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deletePermission(String permissionids,HttpServletRequest request,HttpServletResponse response) {
		try {
			if(permissionids==null||"".equals(permissionids)){
				return returnError("E1003006");
			}
			
			permissionService.deletePermisson(permissionids.split(","));
		} catch (Exception e) {
			JedisUtil.del(Constants.PERMISSONS_NODES);//数据库事务异常时，同时把redis清空，避免数据不同步
			JedisUtil.del(Constants.PERMISSONS_KEY);
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003003");
		}
		return returnSuccess();
	}
	
	/**
	 * 获取所有权限
	 * @param request
	 * @param response
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:53
	 */
	@RequestMapping(value="/allpermissions",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllPermissions(HttpServletRequest request,HttpServletResponse response) {
		List<NPAdminPermission> permissionList=null;
		try {
			permissionList=permissionService.getPermissionListAll();
		} catch (Exception e) {
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003001");
		}
		return returnSuccess(permissionList);
	}
	
	/**
	 * 分页显示权限列表
	 * @param request
	 * @param response
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:53
	 */
	@RequestMapping(value="/permissions",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject listPermissions(HttpServletRequest request,HttpServletResponse response,NPPage page) {
		List<NPAdminPermission> permissionList=null;
		try {
			permissionList=permissionService.getPermissionList(page);
			 HashMap<String, Object> result = new HashMap<String, Object>();
			 result.put("list", permissionList);
			 result.put("totalPage", page.getTotalPage());
			 result.put("totalRecord", page.getTotalRecord());
			 result.put("page", page.getPage());
			 result.put("pageSize", page.getPageSize());
			 return returnSuccess(permissionList);
		} catch (Exception e) {
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003001");
		}
	}
	
	
	/**
	 * 根据roleid获取权限
	 * @param permission
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:28:21
	 */
	@RequestMapping(value="/permissions/{roleid}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getPermissonByRoleId(@PathVariable("roleid") Integer roleid,HttpServletRequest request,HttpServletResponse response) {
		List<NPAdminPermission> permissions=null;
		try {
			if(roleid==null){
				return returnError("E1003006");
			}
			permissions=permissionService.getPermissionListByRoleId(roleid);
		} catch (Exception e) {
			logger.debug("error is :{}",e.getMessage());
			e.printStackTrace();
			return returnError("E1003001");
		}
		return returnSuccess(permissions);
	}
}
