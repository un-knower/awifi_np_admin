package com.awifi.np.admin.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext-test.xml",
"classpath:spring/ApplicationContext-datasource.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class RequestUtilTest {

	
	   private MockHttpServletRequest request;    
	    private MockHttpServletResponse response;    
	      
	    @Before  
	    public void setUp(){  
	        request = new MockHttpServletRequest();    
	        request.setCharacterEncoding("UTF-8");    
	        response = new MockHttpServletResponse();    
	          
	          
	    }  
	      
	    @Test  
	    public void test() {  
	          
	    	RequestUtil.getIpAddr(request);
	    	JSONObject json=new JSONObject();
	    	json.put("name", "dd");
	    	try {
				RequestUtil.writeJson(response,json);
			} catch (Exception e) {
//				e.printStackTrace();
			}
	    }  
}
