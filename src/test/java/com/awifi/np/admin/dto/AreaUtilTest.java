package com.awifi.np.admin.dto;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dbc.entity.CenterPubArea;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.dbc.utils.DBCTokenUtil;
import com.awifi.np.admin.utils.SerializableUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

@RunWith(PowerMockRunner.class) // 1  
@PrepareForTest({ DBCTokenUtil.class}) 
public class AreaUtilTest {

	
	@Before
	public void before() {
		PowerMockito.mockStatic(DBCTokenUtil.class); // 3
		
	}

	@Test
	public void list() {
		JSONObject json = new JSONObject();
		json.put("code", Constants.ECodeSuccess+1);
		when(DBCTokenUtil.getToken()).thenReturn(json);
		Assert.assertEquals(json, AreaUtil.list());

	}
	@Test
	public void test1() throws Exception {
		Assert.assertEquals(null,AreaUtil.getByKey("xxxx"));
	}
	
	@Test
	public void getByKey() throws Exception {
		CenterPubArea area=new CenterPubArea();
		 JedisUtil.set("111",SerializableUtil.serialize(area));
		 AreaUtil.getByKey("111");
		JedisUtil.del("111");
	}
	
	
	@Test
	public void getLongName() throws Exception {
		CenterPubArea area=new CenterPubArea();
		area.setAreaName("beijing");
		JedisUtil.set(Constants.REDIS_AREA_PRE+"1_"+1,SerializableUtil.serialize(area));
        JedisUtil.set(Constants.REDIS_AREA_PRE+"2_"+1,SerializableUtil.serialize(area));
		
		 
		 AreaUtil.getLongName(1L, 1L, 1L);
		JedisUtil.del(Constants.REDIS_AREA_PRE+"1_"+1);
		JedisUtil.del(Constants.REDIS_AREA_PRE+"2_"+1);
	}
}
