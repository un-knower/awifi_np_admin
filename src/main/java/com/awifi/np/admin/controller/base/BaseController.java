package com.awifi.np.admin.controller.base;


import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.utils.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public static String ECodeException = "E1000004";
	public static String ECodeFailed = "E1000005";
	public static String ECodeToken = "E1000007";
	public static String ECodeParam = "E1000001";
	public static String ECodeBadParam = "E1000008";


//	public ModelAndView getModelAndView(){
//		return new ModelAndView();
//	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	
	/**
	 * 成功返回无参数
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午9:00:37
	 */
	public static JSONObject  returnSuccess( ){
		return  MessageUtil.returnSuccess();
	}
	
	/**
	 * 返回成功带data结果
	 * @param data
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:47:11
	 */
	public static JSONObject  returnSuccess(Object data){
		return  MessageUtil.returnSuccess(data);
	}
	/**
	 * 返回指定错误码结果
	 * 替换错误码中的%s等
	 * @param code
	 * @return
	 * @author 王冬冬
	 * @date 2017年1月10日 上午10:46:05
	 */
	public static JSONObject returnError(String code,String value){
		
		return  MessageUtil.commonResult(code, String.format(MessageUtil.getMessage(code),value),null);
	}
	
	/**
	 * 返回错误码带data
	 * @param code
	 * @param data
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:45:44
	 */
	public static JSONObject returnError( String code ,Object data){
		return MessageUtil.returnError(code,data);
	}

	/**
	 * 返回指定错误码结果
	 * @param code
	 * @return
	 * @author 沈叶峰
	 * @date 2017年1月10日 上午10:46:05
	 */
	public static JSONObject  returnError(String code ){
		return  MessageUtil.returnError(code); 
	}
	
//	public NPAdminUser getAdminUser(){
//		return   SessionUtil.getSessionUser();
//	}
	
	public NPAdminUser getAdminUser(HttpServletRequest request){
		return (NPAdminUser) request.getSession().getAttribute(Constants.ADMIN_SESSION_KEY);
	}

	/**
	 * 返回成功带data结果
	 * @param data
	 * @return
	 * @author 沈叶峰
	 * @date 2017年1月10日 上午10:47:11
	 */
	public static JSONObject  returnError(Object data){
		return MessageUtil.returnResult(data);
	}
}
