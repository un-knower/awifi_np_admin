package com.awifi.np.admin.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NPAdminUtilTest {

	
	@Test
	public void test(){
		
		
    NPAdminUtil.getToken("111","1234567890",new Date().getTime());

    Map<String, Object> map=new HashMap<>();
    map.put("access_token", "test");
    Boolean rrr = NPAdminUtil.pingAPI("https://api.weixin.qq.com/cgi-bin/token", map);
    System.out.print(rrr);
//    rrr = NPAdminUtil.pingAPI("https://api.weixin.qq.com/cgi-bin/token", map);
//    System.out.print(rrr);
    
		
	}
}
