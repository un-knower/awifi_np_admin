package com.awifi.np.admin.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.dao.NPAdminLogMapper;
import com.awifi.np.admin.entity.NPAdminLog;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.service.IAdminLogService;
import com.awifi.np.admin.utils.DateUtil;
import com.awifi.np.admin.utils.PropertiesUtil;
import com.awifi.np.admin.utils.RequestUtil;
import com.awifi.np.admin.utils.SessionUtil;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午10:26:39
 * 创建作者：沈叶峰
 * 文件名称：AdminLogServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class AdminLogServiceImpl implements IAdminLogService {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private NPAdminLogMapper adminLogMapper;

	private ExecutorService executorService = Executors.newCachedThreadPool();
	   
	/**
	 * 保存日志
	 * @author 沈叶峰  
	 * @date 2017年1月20日 上午11:06:47
	 */
	public int addAdminLog(NPAdminLog log) {

		return adminLogMapper.insert(log);
	}
	
	/**
	 * 通过线程池开启线程插入日志记录
	 * @author 沈叶峰  
	 * @date 2017年1月20日 上午11:06:56
	 */
	public void createAdminLog(final HttpServletRequest request,
			final HttpServletResponse response, final Exception ex,final  Long startTime,
			final Long endTime) {
		// TODO Auto-generated method stub
		final NPAdminLog log = new NPAdminLog();
		log.setRequestUri(request.getRequestURI());
		log.setMethod(request.getMethod());
		log.setClientIp(RequestUtil.getIpAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setParams(JSONObject.toJSONString(request.getParameterMap()));
		log.setResponseStatus(response.getStatus());
		//当前登录者
		NPAdminUser adminUser = SessionUtil.getSessionUser(request);
		if(adminUser != null){
			log.setCreateUserId(adminUser.getId());
		}


		 executorService.submit(new Runnable() {
	            public void run() {
	                try {
	                    //日志写入数据库
	                	addAdminLog(log);
	                    if (logger.isDebugEnabled()) {
	                    	String message = PropertiesUtil.getConfig("message","LOGINFO");
	                        logger.debug(String.format(message,
	                        		DateUtil.longDateFormat(startTime, DateUtil.YMDHMS_SSS),
	                        		DateUtil.longDateFormat(endTime, DateUtil.YMDHMS_SSS),
	                        		(endTime - startTime) ,log.getRequestUri(),log.getParams()));

	                    }
	                } catch (Exception e) {
	                    logger.error("日志保存失败 :", e);
	                }
	            }
	        });
		
	}


}
