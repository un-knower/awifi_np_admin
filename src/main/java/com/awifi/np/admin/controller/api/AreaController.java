package com.awifi.np.admin.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dbc.entity.CenterPubArea;
import com.awifi.np.admin.dbc.utils.AreaUtil;
import com.awifi.np.admin.utils.SerializableUtil;
import com.awifi.np.admin.utils.redis.JedisPoolUtils;
import com.awifi.np.admin.utils.redis.JedisUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

import static com.awifi.np.admin.utils.redis.JedisUtil.deleteBulk;


/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/15
 * 创建作者：卢朱娜
 * 文件名称：AreaController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/api")
public class AreaController extends BaseController{


    public ArrayList<String> sortKeySet(Set<String> keySet){
        ArrayList keyList = new ArrayList(keySet);
        Collections.sort(keyList, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                String sub0 = arg0.substring(Constants.REDIS_AREA_PRE.length());
                int end = sub0.indexOf("_");
                Integer id0 = Integer.parseInt(sub0.substring(0,end));

                String sub1 = arg1.substring(Constants.REDIS_AREA_PRE.length());
                end = sub1.indexOf("_");
                Integer id1 = Integer.parseInt(sub1.substring(0,end));
                return id0-id1;
            }

        });
        return keyList;
    }

    /**
     * 批量缓存地区，（例子 浙江省31 key：np_admin_area_31_1,value:CenterPubArea对象序列化后的byte[])
     * @param areaList
     * @throws Exception
     */
    private void bulkSetArea(List<CenterPubArea> areaList) throws Exception {
        Jedis jedis = JedisPoolUtils.getJedis();
        try {
            for (CenterPubArea area : areaList) {
                String key = Constants.REDIS_AREA_PRE + area.getId() + "_" + area.getParentId();
                jedis.set(key.getBytes(), SerializableUtil.serialize(area));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JedisPoolUtils.returnRes(jedis);
        }
    }

    /**
     * 缓存下级地区（例子 省份列表 key：np_admin_sub_area_1,value:直接可返回给前端的json串)
     * @param aid
     * @return
     */
    private JSONObject cacheSubArea(Long aid){
        JSONObject robj=null;
        Jedis jedis = JedisPoolUtils.getJedis();
        try{
            Set<String> keySet = jedis.keys(Constants.REDIS_AREA_PRE+"*_"+aid);
            ArrayList<CenterPubArea> areaArrayList = new ArrayList<>();
            if(keySet.size()>0) {
                ArrayList<String> keyList = sortKeySet(keySet);
                for (String key : keyList) {
                    CenterPubArea area = (CenterPubArea) SerializableUtil.unserialize(jedis.get(key.getBytes()));
                    areaArrayList.add(area);
                }
            }
            robj = returnSuccess(areaArrayList);
            jedis.set(Constants.REDIS_SUB_AREA_PRE+aid, JSONObject.toJSONString(robj));
        }catch (Exception e){
            e.printStackTrace();
            robj = returnError("E1031001");
        }finally {
            JedisPoolUtils.returnRes(jedis);
            return robj;
        }

    }



    @RequestMapping(value = "/redis/refresh/area", method = RequestMethod.GET)
    public JSONObject refreshRedisArea(){

        try {
            //清除所有地区缓存
            Set<String> keySet = deleteBulk(Constants.REDIS_AREA_PRE + "*");

            //调用数据中心接口，获取地区数据
            JSONObject jsonObject = AreaUtil.list();
            if(jsonObject.getString("code").equals(Constants.ECodeSuccess)){

                List<CenterPubArea> areaList = JSONObject.parseArray(jsonObject.getString("data"), CenterPubArea.class);
                //缓存地区数据
                bulkSetArea(areaList);

                //清空下级区域的缓存
                deleteBulk(Constants.REDIS_SUB_AREA_PRE + "*");

                return returnSuccess();
            }else{
                return jsonObject;
            }

        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }


    @RequestMapping(value = "/area/{aid}/sub", method = RequestMethod.GET)
    public JSONObject getSubArea(@PathVariable("aid") Long aid){

        //从缓存中读取
        String result = JedisUtil.getValue(Constants.REDIS_SUB_AREA_PRE+aid);

        if(result!=null){
            return JSONObject.parseObject(result);
        }else{
            //缓存中不存在，获取下级的keys
            return  cacheSubArea(aid);
        }
    }


}
