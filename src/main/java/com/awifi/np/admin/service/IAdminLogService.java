package com.awifi.np.admin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awifi.np.admin.entity.NPAdminLog;


/**
 * 管理日志servie
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月20日 上午10:23:27
 * 创建作者：沈叶峰
 * 文件名称：IAdminLogService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IAdminLogService {
	
    /**
     * 记录日志
     * @param log
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午10:25:08
     */
	public int addAdminLog(NPAdminLog log);
	
	/**
	 * 创建管理日志
	 * @param request
	 * @param response
	 * @param ex
	 * @param startTime
	 * @param endTime
	 * @author 沈叶峰 
	 * @date 2017年1月20日 上午10:29:44
	 */
	public void createAdminLog(final HttpServletRequest request, final HttpServletResponse response,
            final Exception ex, final Long startTime, final Long endTime);

}
