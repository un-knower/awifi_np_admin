package com.awifi.np.admin.dto;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dbc.utils.DBCTokenUtil;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

@RunWith(PowerMockRunner.class) // 1  
@PrepareForTest({HttpClientUtil.class,JedisUtil.class})
@SuppressStaticInitializationFor("com.awifi.np.admin.utils.HttpClientUtil")//阻止静态代码块运行 
public class DBTokenUtilsTest {
    
    @InjectMocks
    private DBCTokenUtil dBCTokenUtil;
	@Before
	public void before() {
	    PowerMockito.mockStatic(HttpClientUtil.class);
	    PowerMockito.mockStatic(JedisUtil.class);
	}
	@Test
	public void test() throws Exception{
	    JSONObject json1=new JSONObject();
        json1.put("oauthToken","111111");
        json1.put("loseTimestamp","122222");
        json1.put("oauthTimestamp","1111111");
       
	    JSONObject json=new JSONObject();
        json.put("state", "success");
        json.put("data", json1);
       
        PowerMockito.when(HttpClientUtil.get(anyString(), anyObject())).thenReturn(json.toJSONString());
        String data="data";
	    PowerMockito.when(JedisUtil.getValue(Constants.REDIS_DBC_AT)).thenReturn(data);
	    JSONObject result= dBCTokenUtil.getToken();
	    Assert.assertEquals(data, result.get("data"));
	}
}
