package com.awifi.np.admin.interceptor;

 
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminPermission;
import com.awifi.np.admin.entity.NPAdminRolePermission;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.service.impl.AdminPermissionServiceImpl;
import com.awifi.np.admin.utils.MessageUtil;
import com.awifi.np.admin.utils.RequestUtil;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 
/**
 * 权限拦截器
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月15日 上午15:03:48
 * 创建作者：沈叶峰
 * 文件名称：LogInterceptor.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Autowired
    private AdminPermissionServiceImpl adminPermissionServiceImpl ;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub 
		String path = request.getRequestURI();
		path = path.replaceFirst("^/+", ""); 
		path = path.split(".htm")[0].split(".json")[0];
		String method = request.getMethod();
		if(StringUtil.isNotEmpty(method)){
			method = method.toUpperCase();
		}
		if(path.matches(Constants.NO_INTERCEPTOR_PATH) ){
			return true;
		}else{
			 NPAdminUser adminUser = SessionUtil.getSessionUser(request);
			 if(adminUser != null &&  adminUser.getRoleId() != null ){
				 Integer roleId = adminUser.getRoleId();
//				 if(roleId.equals(1001)) return true;
				List<NPAdminPermission> permissionList = adminPermissionServiceImpl.getPermissionListAll();
				if(permissionList != null && permissionList.size() > 0){
						//判断该请求权限是否在配置中
					NPAdminPermission permission  = findPermission(permissionList,path,method);
						if(permission != null){
							//再检查这个角色是否有权限
							List<NPAdminRolePermission> permissionIdList = adminPermissionServiceImpl.getPermissionIdListByRoleId(roleId);
							boolean hasPerm = false;
							for(NPAdminRolePermission rolePerm:permissionIdList){
								if(permission.getId().equals(rolePerm.getPermissionId())){
									hasPerm = true;
								}
							}
							 //无权限
							if(!hasPerm){
								 this.noPerm(request, response);
							}
							 return hasPerm;	
						}
				}
				 //没有配置的接口直接通过
				 return true;	
			 }else{
				 //未登陆
				 this.notLogin(request,response);
				 return false;	
			 }
		}
			 
	}
	
  
	/**
	 * 递归遍历权限树查找权限
	 * @param permissionList
	 * @param path
	 * @param method
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月18日 下午8:24:15
	 */
	public NPAdminPermission findPermission(List<NPAdminPermission> permissionList,String path,String method) {
		for(NPAdminPermission permission: permissionList ){
			//判断该请求权限是否在配置中
			if(StringUtil.isNotEmpty(permission.getUrl())
				 && StringUtil.isNotEmpty(permission.getMethod())
				 && validateUrl(permission.getUrl(),path)
				 && permission.getMethod().toUpperCase().equals(method)){
				return permission;
			}
			if(permission.getChildPermission() != null && permission.getChildPermission().size() > 0){
				if((permission = findPermission(permission.getChildPermission(), path,method)) != null){
					return permission;
				}
			} 
		}
		return null;
	}
	
	/**
	 * 验证url是否需要权限校验
	 * @param permissionUrl
	 * @param url
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年2月21日 上午9:34:32
	 */
	private static boolean validateUrl(String permissionUrl,String url){
		
		if(StringUtil.isEmpty(permissionUrl) || StringUtil.isEmpty(url)){
			return false;
		}
//		permissionUrl = permissionUrl.replaceAll("\\{[^/]*\\}", "([^/]*)");
		permissionUrl = permissionUrl.replaceAll("\\{[^}]*\\}", "([^/]*)");
		return 	url.matches(permissionUrl);
	}

	 
	private void  notLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  
		 RequestUtil.writeJson(response, MessageUtil.commonResult("E1000002", MessageUtil.getMessage("E1000002"),null));
	}
	
 
	private void  noPerm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  RequestUtil.writeJson(response, MessageUtil.commonResult("E1000003", MessageUtil.getMessage("E1000003"),null));
	}
   
}
