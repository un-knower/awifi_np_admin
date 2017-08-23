package com.awifi.np.admin.utils;

import java.util.Random;

/**
 * 常用工具类
 * @author 沈叶峰
 * 2017年1月9日 下午3:41:05
 */
public class Tools {

	/**
	 * 获取在这个范围内的随机整数
	 * @param bound
	 * @return
	 */
	public static int randInt(int bound){
		Random random = new Random();
		return random.nextInt(bound);
	}

	 /**
     * 检测字符串是否不为空(null,"","null") 
     * @param s
     * @return
     * @author 沈叶峰 
     * @date 2017年1月9日 下午3:42:00
     */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}
	
	 /**
	  * 检测字符串是否为空(null,"","null")
	  * @param s
	  * @return
	  * @author 沈叶峰 
	  * @date 2017年1月9日 下午3:42:56
	  */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}
}
