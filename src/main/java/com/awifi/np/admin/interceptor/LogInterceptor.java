package com.awifi.np.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.service.IAdminLogService;
import com.awifi.np.admin.utils.DateUtil;
import com.awifi.np.admin.utils.PropertiesUtil;

/**
 * 日志记录拦截器
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午10:03:48
 * 创建作者：沈叶峰
 * 文件名称：LogInterceptor.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IAdminLogService  adminLogService;
	
	   private final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	   /**
	    * 请求初始操作
	    * @author 沈叶峰  
	    * @date 2017年1月20日 上午10:06:24
	    */
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
	        // 请求开始时间
	        startTimeThreadLocal.set(System.currentTimeMillis());
	        return super.preHandle(request, response, handler);
	    }
	   
	   /**
	    * 请求结束操作
	    * @author 沈叶峰  
	    * @date 2017年1月20日 上午10:07:50
	    */
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	            throws Exception {
	        // 创建操作日志
//		     adminLogService.createAdminLog(request, response, ex, startTimeThreadLocal.get(), System.currentTimeMillis());//暂时不记录日志
		   if (logger.isDebugEnabled()) {
           	String message = PropertiesUtil.getConfig("message","LOGINFO");
               logger.debug(request.getRequestURI()+JSONObject.toJSONString(request.getParameterMap()));

           }
	        super.afterCompletion(request, response, handler, ex);
	    }

}
