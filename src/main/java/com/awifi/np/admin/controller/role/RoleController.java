package com.awifi.np.admin.controller.role;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminRole;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:10:35
* 创建作者：王冬冬
* 文件名称：RoleController.java
* 版本：  v1.0
* 功能：角色管理
* 修改记录：
*/
@RequestMapping("/admin")
@Controller
public class RoleController extends BaseController{

	@Autowired
	private IAdminRoleService roleService;
	
	/**
	 * 分页列表显示角色
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午9:32:21
	 */
	@RequestMapping(value="/roles",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject listRole(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "keyword", required = false) String keyword,NPPage page){
		
		 List<NPAdminRole> roleList=roleService.listRoles(page,keyword);
		 HashMap<String, Object> result = new HashMap<String, Object>();
		 result.put("list", roleList);
		 result.put("totalPage", page.getTotalPage());
		 result.put("totalRecord", page.getTotalRecord());
		 result.put("page", page.getPage());
		 result.put("pageSize", page.getPageSize());
		return returnSuccess(result);
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param response
	 * @param role
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月22日 上午11:17:16
	 */
	@RequestMapping(value="/role",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addRole(HttpServletRequest request,HttpServletResponse response,@RequestBody NPAdminRole role){
		if(role==null){
		  return returnError("E1004002");
		}
		try {
			role.setCreateDate(new Date());
			roleService.addRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1004001");
		}
		return returnSuccess();
	}
	
	/**
	 * 更新角色
	 * @param request
	 * @param response
	 * @param role
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月22日 上午11:17:35
	 */
	@RequestMapping(value="/role",method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject updateRole(HttpServletRequest request,HttpServletResponse response,@RequestBody NPAdminRole role){
		if(role.getId()==null){
			return returnError("E1004003");
		}
		try {
			roleService.updateRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1004001");
		}
		return returnSuccess();
	}
	
	/**
	 * 根据id获取角色
	 * @param request
	 * @param response
	 * @param roleid
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月22日 上午11:23:09
	 */
	@RequestMapping(value="/role/{id}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getRoleById(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") Integer roleid){
		if(roleid==null){
			return returnError("E1004003");
		}
		NPAdminRole role=null;
		try {
			role=roleService.getRoleById(roleid);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1004001");
		}
		return returnSuccess(role);
	}
	
	/**
	 * 角色删除
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月22日 下午2:09:33
	 */
	@RequestMapping(value="/role",method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deleteRole(HttpServletRequest request,HttpServletResponse response,String ids){
		
		try {
			if(StringUtils.isEmpty(ids)){
				return returnError("E1004003");
			}
			roleService.deteleByIds(ids);
		}catch (BaseException e) {
			return returnError("E1004005");
		} 
		catch (Exception e) {
			e.printStackTrace();
			return returnError("E1004004");
		}
		return returnSuccess();
	}


	/**
	 * 获取角色列表
	 * @param request
	 * @param response
	 * @return
	 * @author 王冬冬
	 * @date 2017年1月17日 上午9:32:21
	 */
	@RequestMapping(value="/allroles",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject allRoles(HttpServletRequest request,HttpServletResponse response){
		List<NPAdminRole> roleList=roleService.allRoleList();
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("list", roleList);
		return returnSuccess(result);
	}
}
