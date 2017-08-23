package com.awifi.np.admin.dbc.utils;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dbc.entity.CenterPubArea;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.PropertiesUtil;
import com.awifi.np.admin.utils.SerializableUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/16
 * 创建作者：卢朱娜
 * 文件名称：AreaUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class AreaUtil extends BaseController{
    public static String areaUrl = PropertiesUtil.getConfig("config", "dbc.area.url");


    //调用数据中心接口，获取所有地区
    public static JSONObject list() {
        JSONObject tmpjobj = DBCTokenUtil.getToken();
        if(!tmpjobj.getString("code").equals(Constants.ECodeSuccess)){
           return tmpjobj;
        }else{
            String token = tmpjobj.getString("data");
            HashMap<String,Object> param = new HashMap<>();
            param.put("status", 1);
            param.put("access_token", token);
            try {
                String result = HttpClientUtil.get(areaUrl, param);
                if(result==null){
                    return BaseController.returnError("E1000012","GET_url:"+areaUrl);
                }
                tmpjobj = JSONObject.parseObject(result);
                if(tmpjobj.getBoolean("suc")){
                    return returnSuccess(tmpjobj.get("rs"));
                }else{
                    return returnError("E1000012", "url:"+areaUrl+" Result:"+result);
                }

            }catch (Exception e){
                return returnError("E1000012", "url:"+areaUrl+" Exception:"+e.getMessage());
            }
        }
    }

    public static String generateAreaKey(Long aid, Long pid){
        return Constants.REDIS_AREA_PRE+aid+"_"+pid;
    }

    /**
     * 从redis缓存中获取地区
     * @return
     */
    public static CenterPubArea getByIdAndPid(Long aid, Long pid) throws Exception {
        return getByKey(generateAreaKey(aid,pid));
    }

    public static CenterPubArea getByKey(String key) throws Exception {
        byte[] result = JedisUtil.get(key);
        if(result==null || result.length==0){
            return null;
        }else{
            return (CenterPubArea) SerializableUtil.unserialize(result);
        }
    }


//    public static CenterPubArea getById(Long aid) throws Exception{
//        Set<String> keyset = JedisUtil.keys(Constants.REDIS_AREA_PRE+aid+"_*");
//        if(keyset.size()>0) {
//            String[] keyList = (String[]) keyset.toArray();
//            return getByKey(keyList[0]);
//        }else{
//            return null;
//        }
//    }

    public static String getLongName(Long pid, Long cid, Long aid) throws Exception {
        String name = "";
        if(pid!=null) {
            CenterPubArea pobj = getByIdAndPid(pid, 1L);
            if (pobj != null) name += pobj.getAreaName();
            if(cid!=null) {
                CenterPubArea cobj = getByIdAndPid(cid, pid);
                if (cobj != null) name += cobj.getAreaName();
                if(aid!=null) {
                    CenterPubArea aobj = getByIdAndPid(aid, cid);
                    if (aobj != null) name += aobj.getAreaName();
                }
            }
        }
        return name;
    }


}
