package com.awifi.np.admin.utils;


import java.util.Random;

/**
 * 字符工具类
 * @author 沈叶峰
 * 2017年1月9日 下午7:27:50
 */
public class StringUtil
{
	/**
	 * 字符为空
	 * @param str
	 * @return
	 * @author 沈叶峰 
	 * @date 2017年1月9日 下午7:28:08
	 */
  public static boolean isEmpty(String str)
  {
    return ((str == null) || (str.equals("")));
  }

  /**
   * 字符不为空
   * @param str
   * @return
   * @author 沈叶峰 
   * @date 2017年1月9日 下午7:28:25
   */
  public static boolean isNotEmpty(String str)
  {
    return ((str != null) && (!(str.equals(""))));
  }

  public static long getChineseTextLen(String str)
  {
    if (isEmpty(str)) return 0L;
    return str.replaceAll("[^\\x00-\\xff]", "00").length();
  }

 
  /**
   * 生成随机数字串
   * @param len
   * @return
   * @author 沈叶峰 
   * @date 2017年1月9日 下午7:30:34
   */
  public static String getNumberRandomString(int len)
  {
    String base = "0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    int number = 0;
    for (int i = 0; i < len; ++i) {
      number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }
 
  /**
   * 生成随机字符串
   * @param len
   * @return
   * @author 沈叶峰 
   * @date 2017年1月9日 下午7:31:03
   */
  public static String getRandomString(int len)
  {
    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    int number = 0;
    for (int i = 0; i < len; ++i) {
      number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

}