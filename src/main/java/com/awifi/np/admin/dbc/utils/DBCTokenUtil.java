package com.awifi.np.admin.dbc.utils;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.PropertiesUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

import java.io.IOException;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/16
 * 创建作者：卢朱娜
 * 文件名称：tokenUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class DBCTokenUtil extends BaseController{

    public static String tonkenUrl = PropertiesUtil.getConfig("config", "dbc.oauthCode.url");

    public static JSONObject getToken(){
        String dbcToken = JedisUtil.getValue(Constants.REDIS_DBC_AT);
        if(dbcToken==null){
            JSONObject jsonObject = callDBC4Token();
            if(jsonObject!=null) {
                return jsonObject;
            }else{
                dbcToken = JedisUtil.getValue(Constants.REDIS_DBC_AT);
            }
        }
        if(dbcToken == null){
            return returnError("E1000011");
        }
        return returnSuccess(dbcToken);
    }

    /**
     *
     * @return
     */
    public static JSONObject callDBC4Token(){
        try {
            String result = HttpClientUtil.get(tonkenUrl, null);
            if(result==null){
                return BaseController.returnError("E1000012","GET_url:"+tonkenUrl);
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getString("state").equals("success")){
                JSONObject data = jsonObject.getJSONObject("data");
                String dbcToken = data.getString("oauthToken");
                Long loseTimestamp = data.getLong("loseTimestamp");
                Long oauthTimestamp = data.getLong("oauthTimestamp");
                int ex = (int) ((loseTimestamp-oauthTimestamp)/1000);
                JedisUtil.setex(Constants.REDIS_DBC_AT, ex, dbcToken);
                return null;
            }else{
                return returnError("E1000010", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("E1000010", e.getMessage());
        }

    }


}
