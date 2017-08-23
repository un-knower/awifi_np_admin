package com.awifi.np.admin.controller.dashboard;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.service.IAdminPermissionService;
import com.awifi.np.admin.utils.SessionUtil;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController{

	@Autowired
	private IAdminPermissionService permisisonService;
	
//    @RequestMapping(value="/index",method=RequestMethod.GET)
//    @ResponseBody
//    public String index(HttpServletRequest request, HttpServletResponse response) {
//
//        return "hello world!";
//    }
    
    /**
     * 返回侧边菜单列表
     * @param request
     * @param response
     * @param roleId
     * @return
     * @author 王冬冬  
     * @date 2017年1月17日 下午2:51:11
     */
    @RequestMapping(value="/index/menus",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject menus(HttpServletRequest request,HttpServletResponse response) {
//    	if(roleId==null){//角色为空
//    		return returnError("E1002002");
//    	}
    	NPAdminUser user=(NPAdminUser) request.getSession().getAttribute(Constants.ADMIN_SESSION_KEY);
    	try {
            List<NPAdminPermission> permissions = permisisonService.getMenusListByRoleId(user.getRoleId());
            return returnSuccess(permissions);
        }catch (Exception e){
    	    e.printStackTrace();
    	    return returnError(Constants.ECodeException);
        }
    }
    
    /**
     * 返回当前登录用户
     * @param request
     * @param response
     * @param roleId
     * @return
     * @author 王冬冬  
     * @date 2017年1月17日 下午2:51:11
     */
    @RequestMapping(value="/index/currentUser",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject getCurrentUser(HttpServletRequest request,HttpServletResponse response) {
        return returnSuccess(SessionUtil.getSessionUser(request));
    }
    
    /**
     * 注销用户
     * @param request
     * @param response
     * @param roleId
     * @return
     * @author 王冬冬  
     * @date 2017年1月17日 下午2:51:11
     */
    @RequestMapping(value="/index/logout",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject logout(HttpServletRequest request,HttpServletResponse response) {
        try {
            SessionUtil.removeSessionUser(request);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }
}
