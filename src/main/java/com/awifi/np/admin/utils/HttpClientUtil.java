package com.awifi.np.admin.utils;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPTemplate;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年2月4日 下午3:13:14
* 创建作者：王冬冬
* 文件名称：HttpClientUtil.java
* 版本：  v1.0
* 功能：http工具类
* 修改记录：
*/
public class HttpClientUtil {
  
	private static Logger logger=LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static final Integer TIMEOUT=30000;
	private static RequestConfig config=null;
	private static CloseableHttpClient httpclient = null;
	private static final Integer MAXTOTAL=300;
	private static final Integer DEFAULTMAXPERROUTE=50;

	static{
		config= RequestConfig.custom().setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).setExpectContinueEnabled(true).build();
		PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
		pccm.setMaxTotal(MAXTOTAL); // 连接池最大并发连接数
		pccm.setDefaultMaxPerRoute(DEFAULTMAXPERROUTE); // 单路由最大并发数
		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
		      public boolean retryRequest(IOException exception , int executionCount , HttpContext context) {
		        // 重试1次,从1开始
		        if (executionCount > 1) {
		          return false;
		        }
		        if (exception instanceof NoHttpResponseException) {
		        	logger.info("[NoHttpResponseException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
		          return true;
		        }
		        else if (exception instanceof SocketException) {
		        	logger.info("[SocketException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
		          return true;
		        }
		        return false;
		      }
		    };
		    httpclient = HttpClients.custom().setConnectionManager(pccm).setDefaultRequestConfig(config).setRetryHandler(retryHandler).build();
	}
	
//	public static void main(String[] args) {
//
//		Map<String,Object> map=new HashMap<>();
//		map.put("page", 1);
//		map.put("pageSize", 20);
//		JSONObject json=new JSONObject();
//		json.put("name", "dd");
//		map.put("params", json);
//
//		NPAdminRole role=new NPAdminRole();
//		role.setId(11);
//		role.setRoleName("测试角色");
//
//		try {
//		String result=get("http://localhost:8080/awifi_np_admin/admin/roles",map);
////		String result=post("http://localhost:8080/awifi_np_admin/admin/role",role);
//

//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}

	public static ResponseHandler generateResponseHandler(){
		return new ResponseHandler<String>() {

			@Override
			public String handleResponse(
					final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}

			}

		};
	}


	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author 王冬冬  
	 * @date 2017年2月6日 上午9:45:12
	 */
	public static String get(String url, Map<String, Object> param) throws Exception{
		    String responseBody=null;
		    HttpGet httpget=null;
	        try {
	           
	        	String tureUrl=url;
	            List<NameValuePair> params = new ArrayList<NameValuePair>();  
                if(param!=null&&!param.isEmpty()){ 
		            for(String key:param.keySet()){
		            	params.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
		            }
		            tureUrl=url+"?"+URLEncodedUtils.format(params, HTTP.UTF_8);
                }
	            
	            httpget = new HttpGet(tureUrl);
				logger.debug(tureUrl);
//	            RequestConfig gConfig= RequestConfig.custom().setConnectTimeout(TIMEOUT*3).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT*4).build();
	            httpget.setConfig(config);
	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = generateResponseHandler();
	            responseBody = httpclient.execute(httpget, responseHandler);
				logger.debug(responseBody);

			} finally {
//	            httpclient.close();
				if(httpget!=null) {
					httpget.releaseConnection();
				}
	        }
			return responseBody;
		
	}
	
//	/**
//	 * @param url
//	 * @param param
//	 * @return
//	 * @throws ClientProtocolException
//	 * @throws IOException
//	 * @author 王冬冬  
//	 * @date 2017年2月6日 上午9:45:23
//	 */
//	public static String post(String url,Map<String, Object> param) throws ClientProtocolException, IOException{
//		    String responseBody=null;
//	        try {
//	        	
//	            HttpPost httppost = new HttpPost(url);
//	            httppost.setConfig(config);
//	            StringEntity entity=new StringEntity(JSON.toJSONString(param),HTTP.UTF_8);
//	            httppost.setEntity(entity);
//	            // Create a custom response handler
//	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//	
//	                @Override
//	                public String handleResponse(
//	                        final HttpResponse response) throws ClientProtocolException, IOException {
//	                    int status = response.getStatusLine().getStatusCode();
//	                    if (status >= 200 && status < 300) {
//	                        HttpEntity entity = response.getEntity();
//	                        return entity != null ? EntityUtils.toString(entity) : null;
//	                    } else {
//	                        throw new ClientProtocolException("Unexpected response status: " + status);
//	                    }
//	                }
//	
//	            };
//	            responseBody = httpclient.execute(httppost, responseHandler);
//	        } finally {
//	            httpclient.close();
//	        }
//			return responseBody;
//	}
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author 王冬冬  
	 * @date 2017年2月6日 上午9:45:23
	 */
	public static String post(String url,Object param) throws Exception{
		    String responseBody=null;
		    HttpPost httppost=null;
	        try {
	        	
	            httppost = new HttpPost(url);
//	            RequestConfig pConfig= RequestConfig.custom().setConnectTimeout(TIMEOUT*3).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT*4).build();
	            httppost.setConfig(config);
	            StringEntity entity=new StringEntity(JSON.toJSONString(param),HTTP.UTF_8);
	            entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
	            httppost.setEntity(entity);
	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = generateResponseHandler();
	            responseBody = httpclient.execute(httppost, responseHandler);
				logger.debug(responseBody);
	        } finally {
//	            httpclient.close();
	            httppost.releaseConnection();
	        }
			return responseBody;
	}


	@SuppressWarnings("finally")
	public static String post(String url, HashMap<String,Object> paramMap, String requestBody)throws Exception{
		HttpPost httpPost = null;
		String responseBody=null;

		try {
			String trueUrl = url;

			//设置param参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(paramMap!=null&&!paramMap.isEmpty()){
				for(String key:paramMap.keySet()){
					params.add(new BasicNameValuePair(key, String.valueOf(paramMap.get(key))));
				}
				trueUrl=trueUrl+"?"+URLEncodedUtils.format(params, "UTF-8");
			}

			logger.debug(trueUrl);
			logger.debug(requestBody);

			httpPost = new HttpPost(trueUrl);
			httpPost.setConfig(config);

			//设置请求体参数
			StringEntity entity=new StringEntity(requestBody,"UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);

			ResponseHandler<String> responseHandler = generateResponseHandler();
			responseBody = httpclient.execute(httpPost, responseHandler);
			logger.debug(responseBody);

		}finally {
			httpPost.releaseConnection();
			return responseBody;
		}

	}

	@SuppressWarnings("finally")
	public static String put(String url, HashMap<String,Object> paramMap, String requestBody)throws Exception{
		HttpPut httpPut = null;
		String responseBody=null;

		try {
			String trueUrl = url;

			//设置param参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(paramMap!=null&&!paramMap.isEmpty()){
				for(String key:paramMap.keySet()){
					params.add(new BasicNameValuePair(key, String.valueOf(paramMap.get(key))));
				}
				trueUrl=trueUrl+"?"+URLEncodedUtils.format(params, "UTF-8");
			}

			logger.debug(trueUrl);
			logger.debug(requestBody);

			httpPut = new HttpPut(trueUrl);
			httpPut.setConfig(config);

			//设置请求体参数
			StringEntity entity=new StringEntity(requestBody,"UTF-8");
			entity.setContentType("application/json");
			httpPut.setEntity(entity);

			ResponseHandler<String> responseHandler = generateResponseHandler();
			responseBody = httpclient.execute(httpPut, responseHandler);
			logger.debug(responseBody);

		}finally {
			httpPut.releaseConnection();
			return responseBody;
		}

	}
	@SuppressWarnings("finally")
	/**
	 * @param url
	 * @param param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author 王冬冬  
	 * @date 2017年2月6日 上午9:45:12
	 */
	public static String delete(String url, Map<String, Object> param) throws Exception{
		    String responseBody=null;
		    HttpDelete httpdelete=null;
	        try {
	           
	        	String tureUrl=url;
	            List<NameValuePair> params = new ArrayList<NameValuePair>();  
                if(param!=null&&!param.isEmpty()){ 
		            for(String key:param.keySet()){
		            	params.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
		            }
		            tureUrl=url+"?"+URLEncodedUtils.format(params, HTTP.UTF_8);
                }
	            
                httpdelete = new HttpDelete(tureUrl);
				logger.debug(tureUrl);
//	            RequestConfig gConfig= RequestConfig.custom().setConnectTimeout(TIMEOUT*3).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT*4).build();
                httpdelete.setConfig(config);
	            // Create a custom response handler
	            ResponseHandler<String> responseHandler = generateResponseHandler();
	            responseBody = httpclient.execute(httpdelete, responseHandler);
				logger.debug(responseBody);

			} finally {
//	            httpclient.close();
				if(httpdelete!=null) {
					httpdelete.releaseConnection();
				}
	        }
			return responseBody;
		
	}
public static void main(String[] args) throws Exception {
//
//		for(int i=0;i<1000;i++){
//			String url = "http://192.168.4.84:8080/admin/service/"+i;
//			get(url,null);
//		}
//
	
//	NPTemplate template=new NPTemplate();
//	 template.setSrc("2");
//	    template.setTemplateName("namedddd");
//	   	template.setContent("<html></html>");
//	   	template.setSuitCode("test_suit");
//	   	template.setTemplateCode("test_template");
//	   	template.setServiceCode("service_code");
//	   	template.setRemark(template.getTemplateName());
//	   	template.setCreateDate(new Date());
//	   	template.setUpdateDate(template.getCreateDate());
//	System.currentTimeMillis();
//	HashMap<String,Object> map=new HashMap<String,Object>();
//	map.put("timestamp", String.valueOf(System.currentTimeMillis()));
//	map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+String.valueOf(System.currentTimeMillis())));
//	String str=JSONObject.toJSONString(template);
//    HttpClientUtil.put("http://localhost:8081/awifi-np-biz-project/projsrv/am/template",map,str);
    
//    NPTemplate template=new NPTemplate();
//	template.setContent("hello");
//	template.setSuitCode("test_suit");
//   	template.setTemplateCode("test_template");
//   	template.setServiceCode("service_code");
//	System.currentTimeMillis();
//	HashMap<String,Object> map=new HashMap<String,Object>();
//	map.put("timestamp", String.valueOf(System.currentTimeMillis()));
//	map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+String.valueOf(System.currentTimeMillis())));
//	map.put("serviceCode", template.getServiceCode());
//	map.put("suitCode", template.getSuitCode());
//	map.put("templateCode", template.getTemplateCode());
//    HttpClientUtil.delete("http://localhost:8081/awifi-np-biz-project/projsrv/am/template",map);
    
//    NPTemplate template=new NPTemplate();
//   	template.setSuitCode("test_suit");
//	template.setTemplateCode("test_template");
//	template.setServiceCode("service_code");
//   	HashMap<String,Object> map=new HashMap<String,Object>();
//   	String timestamp=String.valueOf(System.currentTimeMillis());
//   	map.put("timestamp",timestamp);
//   	map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+timestamp));
//   	map.put("serviceCode", template.getServiceCode());
//   	map.put("suitCode", template.getSuitCode());
//   	map.put("templateCode", template.getTemplateCode());
//    HttpClientUtil.get("http://localhost:8081/awifi-np-biz-project/projsrv/am/template",map);
//    List<NPTemplate> list=new ArrayList<NPTemplate>();
//    NPTemplate template=new NPTemplate();
//   	template.setContent("hello");
//   	template.setSuitCode("xxx");
//   	template.setTemplateCode("xxx");
//   	template.setServiceCode("xxx");
//   	list.add(template);
//   	HashMap<String,Object> map=new HashMap<String,Object>();
//   	String timestamp=String.valueOf(System.currentTimeMillis());
//   	map.put("timestamp",timestamp);
//   	map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+timestamp));
////   	map.put("serviceCode", template.getServiceCode());
////   	map.put("suitCode", template.getSuitCode());
////   	map.put("templateCode", template.getTemplateCode());
//    HttpClientUtil.post("http://localhost:8081/awifi-np-biz-project/projsrv/am/templates",map,JSONObject.toJSONString(list));
    
//    NPTemplate template=new NPTemplate();
//    template.setSrc("2");
//    template.setTemplateName("name");
//   	template.setContent("<html></html>");
//   	template.setSuitCode("test_suit");
//   	template.setTemplateCode("test_template");
//   	template.setServiceCode("service_code");
//   	template.setRemark(template.getTemplateName());
//   	template.setCreateDate(new Date());
//   	template.setUpdateDate(template.getCreateDate());
//   	HashMap<String,Object> map=new HashMap<String,Object>();
//   	String timestamp=String.valueOf(System.currentTimeMillis());
//   	map.put("timestamp",timestamp);
//   	map.put("token", MD5.md5("S_PROJ"+"de65864c22a2e75f79217416c41a5604"+timestamp));
//   	map.put("serviceCode", template.getServiceCode());
//   	map.put("suitCode", template.getSuitCode());
//   	map.put("templateCode", template.getTemplateCode());
//    HttpClientUtil.post("http://localhost:8081/awifi-np-biz-project/projsrv/am/template",map,JSONObject.toJSONString(template));
}


}
