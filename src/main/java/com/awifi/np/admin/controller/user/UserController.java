package com.awifi.np.admin.controller.user;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awifi.np.admin.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.service.IAdminUserService;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 上午10:12:08
* 创建作者：王冬冬
* 文件名称：UserController.java
* 版本：  v1.0
* 功能：账户管理
* 修改记录：
*/
@RequestMapping("/admin")
@Controller
public class UserController extends BaseController{
	
	@Autowired
	private IAdminUserService userService;
	
	/**
	 * 分页列表实现用户
	 * @param request
	 * @param response
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月17日 上午10:57:03
	 */
	@RequestMapping(value="/users",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject listUsers(HttpServletRequest request,HttpServletResponse response,NPPage page,@RequestParam(value = "keyword", required = false) String keyword,Integer keywordtype){
		
		List<NPAdminUser> userList=userService.listUsers(page,keyword,keywordtype);
		 HashMap<String, Object> result = new HashMap<String, Object>();
		 result.put("list", userList);
		 result.put("totalPage", page.getTotalPage());
		 result.put("totalRecord", page.getTotalRecord());
		 result.put("page", page.getPage());
		 result.put("pageSize", page.getPageSize());
		 result.put("keyword",keyword);
		return returnSuccess(result);
	}

	/**
	 * 新增用户
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午5:17:02
	 */
	@RequestMapping(value="/user",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addUser(@RequestBody NPAdminUser user,HttpServletRequest request,HttpServletResponse response){
		 
		try {
			userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1005002");
		}
		return returnSuccess();
	}
	
	/**
	 * 修改用户
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午5:17:02
	 */
	@RequestMapping(value="/user",method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject updateUser(HttpServletRequest request,HttpServletResponse response,@RequestBody NPAdminUser user){
		 
		try {
			userService.updateUser(user);
			request.getSession().setAttribute(Constants.ADMIN_SESSION_KEY,userService.getUserById(user.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1005004");
		}
		return returnSuccess();
	}

	/**
	 * 根据Id获取用户
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午5:17:02
	 */
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getUserById(HttpServletRequest request,HttpServletResponse response,@PathVariable Integer id){
		NPAdminUser user=null; 
		try {
			user=userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1005001");
		}
		return returnSuccess(user);
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午5:17:02
	 */
	@RequestMapping(value="/user",method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deleteUser(HttpServletRequest request,HttpServletResponse response,String ids){
		
		try {
			if(ids==null||"".equals(ids)){
				return returnError("E1005005");
			}
			userService.deteleByIds(ids.split(","));
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1005003");
		}
		return returnSuccess();
	}
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author 王冬冬  
	 * @date 2017年1月20日 下午5:17:02
	 */
	@RequestMapping(value="/user/password",method=RequestMethod.PUT)
	@ResponseBody
	public JSONObject updatePassword(HttpServletRequest request,HttpServletResponse response,@RequestBody NPAdminUser user){
		 
		try {
			userService.updatePassword(user);
		} catch (Exception e) {
			e.printStackTrace();
			return returnError("E1005004");
		}
		return returnSuccess();
	}
}
