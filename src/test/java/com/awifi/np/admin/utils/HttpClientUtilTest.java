package com.awifi.np.admin.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpClientUtilTest {

	@Test
	public void test_get(){
		
		String url = "http://192.168.4.84:8080/admin/service/";
		try {
			HttpClientUtil.get(url,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_post(){
		
		String url = "http://192.168.4.84:8080/admin/service/";
		try {
			HttpClientUtil.post(url, "hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void test_post_1(){
		
		String url = "http://192.168.4.84:8080/admin/service/";
		try {
			String requestBody = null;
			Map map=new HashMap();
			map.put("name","dd");
			HttpClientUtil.post(url, null, requestBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
    public void test_put(){
        
        String url = "http://192.168.5.84:8080/admin/service/";
        try {
            String requestBody = null;
            HttpClientUtil.put(url, null, requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	@Test
    public void test_delete(){
        
        String url = "http://192.168.4.84:8080/admin/service/";
        try {
            String requestBody = null;
            HttpClientUtil.delete(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
	
	@Test
	public void test(){
	    
	    String url="http://localhost:8081/testclient/name/test";
	    long time1=System.currentTimeMillis();
	    try {
            HttpClientUtil.get(url,null);
            System.out.println(System.currentTimeMillis()-time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	}
	
	
}
