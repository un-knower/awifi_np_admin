package com.awifi.np.admin.utils;
import java.util.ResourceBundle;
/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期:2017年1月10日 上午9:39:35
 * 创建作者：沈叶峰
 * 文件名称：PropertiesUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class PropertiesUtil {

	
	private static final String configPath =  ResourceBundle.getBundle("main").getString("project.config.path");

    /**
     * 取得参数
     * @param fileName
     * @param key
     * @return
     * @author 沈叶峰 
     * @date 2017年1月10日 上午9:50:05
     */
	public static String getConfig(String fileName,String key) {
		if("config".equals(fileName) ||  "redis".equals(fileName)){
			fileName = configPath + "/" + fileName;
		}
		return ResourceBundle.getBundle(fileName).getString(key);
	}
 
	public static void main(String[] args) {
//		String ff = getConfig("message", "E_10001");

	}
}