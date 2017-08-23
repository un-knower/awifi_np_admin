package com.awifi.np.admin.exception;
/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月13日 下午4:12:02
* 创建作者：王冬冬
* 文件名称：LoginException.java
* 版本：  v1.0
* 功能：
* 修改记录：
*/
public class BaseException extends Exception{

	/**
	 * 
	 */
	private String message;
	private static final long serialVersionUID = 1L;
	public BaseException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return this.message;
	}
    
}
