package com.awifi.np.admin.utils;

import com.alibaba.fastjson.JSONObject;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月10日 上午9:45:41
 * 创建作者：沈叶峰
 * 文件名称：MessageUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class MessageUtil {
	
	/**
	 * 无变量返回错误消息
	 * @param code
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:43:20
	 */
	public static String getMessage(String code) {
		String message = PropertiesUtil.getConfig("message",code);
		return message;
	}
	
	/**
	 * 可变错误消息
	 * @param code
	 * @param args
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:43:40
	 */
	public static String getMessage(String code,Object[] args) {
		String message = PropertiesUtil.getConfig("message",code);
		if(StringUtil.isNotEmpty(message)){
			message = String.format(message, args);
		}
		return message;
	}
	
	/**
	 * 成功返回无参数
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午9:00:37
	 */
	public static JSONObject  returnSuccess( ){
		return  commonResult("0", getMessage("0"),null);
	}
	
	/**
	 * 返回成功带data结果
	 * @param data
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:47:11
	 */
	public static JSONObject  returnSuccess(Object data){
		return  commonResult("0", MessageUtil.getMessage("0"),data);
	}
	
	/**
	 * 返回错误码带data
	 * @param code
	 * @param data
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 上午10:45:44
	 */
	public static JSONObject  returnError( String code ,Object data){
		return  commonResult(code,  getMessage(code),data);
	}

	/**
	 * 返回指定错误码结果
	 * @param code
	 * @return
	 * @author 沈叶峰
	 * @date 2017年1月10日 上午10:46:05
	 */
	public static JSONObject  returnError(String code ){
		return  commonResult(code,  getMessage(code),null);
	}
	
	/**
	 * 返回通用消息信息
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月10日 下午4:25:44
	 */
	public static  JSONObject  commonResult( String code, String msg,Object data){
		JSONObject json = new JSONObject();
		json.put("code",code);
		json.put("msg",msg);
		json.put("data",data);
		return json;
	}

	public static JSONObject returnResult(Object data) {
		return   (JSONObject) JSONObject.toJSON(data);
	}
	public static void main(String[] args) {
		
//	 getMessage("E_10001",new String[]{"param1","E_10001"});

	}


}
