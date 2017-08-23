package com.awifi.np.admin.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminUser;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午10:55:51
 * 创建作者：沈叶峰
 * 文件名称：SessionUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class SessionUtil {

	
	/**
	 * 获得当前登录用户
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月20日 上午10:59:46
	 */
//   public static NPAdminUser getSessionUser() {
//	        return (NPAdminUser)getsession().getAttribute(Constants.ADMIN_SESSION_KEY);
//   }

   public static NPAdminUser getSessionUser(HttpServletRequest request) {
	   HttpSession session;
	   try {
		   session = request.getSession();
	   }catch (Exception e){
	   	   return null;
	   }
       return (NPAdminUser)session.getAttribute(Constants.ADMIN_SESSION_KEY);
}
	
    /**
     * 获取一个session
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午10:58:34
     */
    public static HttpSession getsession() {
        return getRequest().getSession();
    }

	  /**
	   * 获取一个请求
	   * @return
	   * @author 沈叶峰 
	   * @date 2017年1月20日 上午10:58:12
	   */
	  public static HttpServletRequest getRequest()
	    {
	      ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	      HttpServletRequest request = requestAttributes.getRequest();
	      return request;
	    }

	/**
	 * 删除session中的用户信息
	 * @param request
	 * @author 王冬冬  
	 * @date 2017年2月13日 下午2:33:59
	 */
	public static void removeSessionUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.ADMIN_SESSION_KEY);
	}
}
