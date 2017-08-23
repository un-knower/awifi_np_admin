package com.awifi.np.admin.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.TraceMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;


/**
 * http工具类
 * @author 沈叶峰
 * 2017年1月9日 下午7:16:47
 */
public class RequestUtil {
	
	 protected static Log log = LogFactory.getLog(RequestUtil.class);
	 
	 	/**
		 * 获取客户端真实ip
		 * @param request
		 * @return
		 * @author 沈叶峰 
		 * @date 2017年1月20日 上午10:37:12
		 */
	   public static String getIpAddr(HttpServletRequest request) {  
		        String ip = request.getHeader("x-forwarded-for");  
		  
		        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		            ip = request.getHeader("Proxy-Client-IP");  
		        }  
		  
		        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		            ip = request.getHeader("WL-Proxy-Client-IP");  
		        }  
		  
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		        }  
		  
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)||ip.startsWith("10.")) {  
		            ip = request.getRemoteAddr();  
		        }  
		        return ip;  
	  }  
	
	/**
	 * 返回json
	 * @param response
	 * @param json
	 * @throws Exception
	 * @author 沈叶峰 
	 * @date 2017年1月10日 下午4:33:57
	 */
	public static void writeJson(HttpServletResponse response, JSONObject json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        	
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}