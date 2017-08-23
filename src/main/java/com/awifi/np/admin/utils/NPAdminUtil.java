package com.awifi.np.admin.utils;

import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.entity.NPService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/17
 * 创建作者：卢朱娜
 * 文件名称：NPAdminUtil.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class NPAdminUtil {
    public static Boolean checkToken(String result, String code, String key, Long timestamp){
        if (result==null) return false;
        return result.equals(MD5.md5(code+key+timestamp.toString()));
    }

    public static String getToken(String code, String key,Long timestamp){
        return MD5.md5(code+key+String.valueOf(timestamp));
    }

    /**
     * 判断权限接口能否访问
     * @param
     * @return
     */
    public static Boolean pingCheckAuth(NPService npService){
        String url = getHost(npService.getServiceHost(), npService.getServicePort())+npService.getCheckAuth();
        Map<String, Object> map=new HashMap<>();
        map.put("access_token", "test");
        map.put("roleIds", 0);
        map.put("interfacecode", "test");
        return pingAPI(url, map);
    }

    /**
     * 判断安全接口能否访问
     * @param
     * @return
     */
    public static Boolean pingCheckSafe(NPService npService){
        String url = getHost(npService.getServiceHost(), npService.getServicePort()) + npService.getCheckSafe();
        Map<String, Object> map=new HashMap<>();
        map.put("access_token", "test");
        return pingAPI(url, map);
    }


    public static Boolean pingAPI(String url, Map<String, Object> param){
        try {
            String result  = HttpClientUtil.get(url, param);
            return result!=null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getHost(String host, String port){
        if(StringUtil.isEmpty(port)||port.equals("80")){
            return host;
        }else{
            return host+":"+port;
        }
    }

    public static Boolean isPublishOver(Byte status){
        if(status!=null){
            return (status == Constants.PS_PUBLISHED || status == Constants.PS_CANCEL);
        }
        return false;
    }


//    public static void main(String... args){
//
//        Map<String, Object> map=new HashMap<>();
//        map.put("access_token", "test");
//        Boolean rrr = pingAPI("https://api.weixin.qq.com/cgi-bin/token", map);
//        System.out.print(rrr);
//        rrr = pingAPI("https://api.weixin.qq.com/cgi-bin/token", map);
//        System.out.print(rrr);
//    }
}
