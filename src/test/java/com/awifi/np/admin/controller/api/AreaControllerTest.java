package com.awifi.np.admin.controller.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.dbc.entity.CenterPubArea;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.SerializableUtil;
import com.awifi.np.admin.utils.redis.JedisPoolUtils;
import com.awifi.np.admin.utils.redis.JedisUtil;

import redis.clients.jedis.Jedis;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/3/22
 * 创建作者：卢朱娜
 * 文件名称：AreaControllerTest.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
//表示使用Spring Test组件进行单元测试
@RunWith(PowerMockRunner.class)
@PrepareForTest(AreaUtil.class)
public class AreaControllerTest {


    @InjectMocks
    AreaController controller;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(AreaUtil.class);
//        PowerMockito.mockStatic(JedisPoolUtils.class);
    }

    @Test
    public void testArea() throws Exception {
    	CenterPubArea area=new CenterPubArea();
    	area.setId(null);
    	area.setParentId(null);
//    	 String key = Constants.REDIS_AREA_PRE + 11 + "_" + 1;
//    	 Jedis jedis = JedisPoolUtils.getJedis();
//    	 jedis.set(key.getBytes(), SerializableUtil.serialize(area));
    	JSONObject json=new JSONObject();
    	json.put("code", "0");
    	PowerMockito.when(AreaUtil.list()).thenReturn(json);
    	JSONObject result =controller.refreshRedisArea();
    	Assert.assertEquals(result.get("code"), "0");
    	
    	List<CenterPubArea> list=new ArrayList<CenterPubArea>();
    	list.add(area);
//        jedis.del(key.getBytes());
    	json.put("data",list);
    	PowerMockito.when(AreaUtil.list()).thenReturn(json);
        JSONObject result1 =controller.refreshRedisArea();
        Assert.assertEquals(result1.get("code"),Constants.ECodeException);
        
        json.put("data",JSON.toJSONString(list));
        PowerMockito.when(AreaUtil.list()).thenReturn(json);
        JSONObject result2 =controller.refreshRedisArea();
        Assert.assertEquals(result2.get("code"),"0");
    	Jedis jedis = JedisPoolUtils.getJedis();
    	jedis.del((Constants.REDIS_AREA_PRE + area.getId() + "_" + area.getParentId()).getBytes());
    }
    
    @Test
    public void testgetSubArea() throws Exception {
//        mockMvc.perform(get("/api/area/1/sub")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.code").value(Constants.ECodeSuccess));
        
        JedisUtil.del(Constants.REDIS_SUB_AREA_PRE+1);
        JedisUtil.del(Constants.REDIS_AREA_PRE+"1_"+1);
        JedisUtil.del(Constants.REDIS_AREA_PRE+"2_"+1);
        CenterPubArea area=new CenterPubArea();
        JedisUtil.set(Constants.REDIS_AREA_PRE+"1_"+1,SerializableUtil.serialize(area));
        JedisUtil.set(Constants.REDIS_AREA_PRE+"2_"+1,SerializableUtil.serialize(area));
        controller.getSubArea(1L);
        JedisUtil.del(Constants.REDIS_AREA_PRE+"1_"+1);
        JedisUtil.del(Constants.REDIS_AREA_PRE+"2_"+1);
//        controller.getSubArea(1L);
        JedisUtil.del(Constants.REDIS_SUB_AREA_PRE+1);
    }
    @Test
    public void testsortKeySet() {
        Set<String> set=new HashSet<String>();
        set.add("np_admin_area_11_");
        set.add("np_admin_area_12_");
        controller.sortKeySet(set);
    }
}
