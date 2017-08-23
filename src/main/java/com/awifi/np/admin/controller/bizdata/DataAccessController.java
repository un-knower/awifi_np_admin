package com.awifi.np.admin.controller.bizdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.exception.BaseException;
import com.awifi.np.admin.service.IInterfaceService;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

/**
* 版权所有： 爱WiFi无线运营中心
* 创建日期:2017年1月23日 上午11:04:11
* 创建作者：王冬冬
* 文件名称：DataAccessController.java
* 版本：  v1.0
* 功能：数据接口
* 修改记录：
*/
@RequestMapping("/externalapi")
@Controller
public class DataAccessController extends BaseController{
    
    /**
     * 接口service
     */
    @Autowired
    private IInterfaceService interfaceService;
    
    /**
     * 数据接口
     * @param request 请求
     * @param response 响应
     * @param access_token 令牌
     * @param servicecode 服务编码
     * @param interfacecode 接口编码
     * @param params 参数
     * @return json
     * @author 王冬冬  
     * @date 2017年7月21日 上午9:22:32
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/interface/data",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject dataAccess(HttpServletRequest request,HttpServletResponse response,String access_token,String servicecode,String interfacecode,String params){
        NPInterface npinterface=null;
        NPService service=null;
        try {
            npinterface = getInterface(servicecode, interfacecode);
            if(npinterface==null){
                return returnError("E1009002");//该接口未注册
            }
            service=npinterface.getService();
            if(service==null){
                return returnError("E1015001");//服务未注册
            }
            if(service.getPublishStatus().byteValue()!=Constants.SERVICR_ON_LINE){//如果服务状态不是已上线
                return returnError("E0000006");//服务不在已上线状态
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("E1009001",e.getMessage());
        }
        String value=null;
        if(!npinterface.getIfcheck()){//如果不需要校验，有用户信息则返回用户信息
            return getDataByAccessToken(access_token);
        }
          //获取access_token的值
        value=JedisUtil.getValue(access_token);
        if(StringUtils.isBlank(value)){
            return returnError("E0000001");
        }
        Map<String, Object> resultMap = JSON.parseObject(value, Map.class);//转成map
        if(resultMap==null||resultMap.isEmpty()){
            return returnError("E1009004");
        }
        Map<String, Object> info=getInfo(value,access_token,resultMap);//从access_token获取数据
        return executeSafeAndAuth(access_token, servicecode, interfacecode, params, service, info);
    }

    /**
     * 从access_token获取数据
     * @param value 数据
     * @param access_token 令牌
     * @return map
     * @author 王冬冬  
     * @param resultMap 
     * @date 2017年7月19日 下午4:10:28
     */
    private Map<String, Object> getInfo(String value, String access_token, Map<String, Object> resultMap) {
        Map<String, Object> dataMap=new HashMap<String, Object>(1);
        if(access_token.startsWith("AT_APP_")){
            Map<String, Object> appInfo = (Map<String,Object>)resultMap.get("appInfo");//获取appInfo数据
            dataMap.put("appInfo", appInfo);
                
        }else{
            JedisUtil.setex(access_token,Constants.TOKEN_EXPIRE_TIME,value);//更新redis
            Map<String, Object> userInfo = (Map<String,Object>)resultMap.get("userInfo");//获取userInfo数据
            dataMap.put("userInfo", userInfo);
        }
        return dataMap;
    }

//    /**
//     * 从access_token获取数据
//     * @param value
//     * @param access_token
//     * @return
//     * @author 王冬冬  
//     * @date 2017年7月19日 下午4:11:26
//     */
//    private Map<String, Object> getDataByAccessTokenParseToMap(String value,String access_token) {
//        
//        Map<String, Object> resultMap = JSON.parseObject(value, Map.class);//转成map
//        if(resultMap==null){
//            return returnError("E1009004");
//        }
//        return resultMap;
//    }

    /**
     * 执行安全和权限接口，并返回
     * @param access_token 令牌
     * @param servicecode 服务编码
     * @param interfacecode 接口编码
     * @param params 参数
     * @param service 服务
     * @param info 获取的数据 
     * @return json
     * @author 王冬冬  
     * @date 2017年7月19日 下午3:58:41
     */
    private JSONObject executeSafeAndAuth(String access_token, String servicecode, String interfacecode, String params,
            NPService service, Map<String, Object> info) {
        /*调用安全接口*/
        String checkSafe=null;
        try {
            checkSafe = checkSafe(interfacecode,servicecode,access_token,params,service);
        } catch (BaseException e) {
            e.printStackTrace();
            return returnError("E1009001",e.getMessage());
        }
        JSONObject safeJson=JSONObject.parseObject(checkSafe);
        if(checkSafe!=null&&!"0".equals(safeJson.get("code"))){
            return returnError(safeJson);
        }
        /*调用权限接口*/
        String checkAuthResult=null;
        Map<String,Object> appinfo=(Map<String, Object>) info.get("appInfo");
        Map<String,Object> userInfo=(Map<String, Object>) info.get("userInfo");
        try {
            checkAuthResult = checkAuth(servicecode,interfacecode,access_token,params,service,appinfo,userInfo);
        } catch (BaseException e) {
            e.printStackTrace();
            return returnError("E1009001",e.getMessage());
        }   
        JSONObject authJson=JSONObject.parseObject(checkAuthResult);
        if(checkAuthResult!=null&&!"0".equals(authJson.get("code"))){
            return returnError(authJson);
        }
        if(appinfo!=null){
            return returnSuccess(appinfo);
        }else{
            return returnSuccess(userInfo); 
        }
    }
//    /**
//     * app权限接口调用
//     * @param roleIds
//     * @param interfacecode
//     * @param access_token
//     * @return
//     * @author 王冬冬  
//     * @date 2017年2月6日 上午9:52:52
//     */
//    public String checkAPPAuth(Map<String, Object> appInfo,String serviceCode,String interfacecode, String access_token,String params,NPService npService) throws BaseException{
//        try {
//            
////          List<NPService> services=iServiceService.getByServiceCode(serviceCode);
////          NPService npService=services.get(0);
//            String url = buildAuthUrl(npService);
//            Map<String, Object> map=new HashMap<String,Object>();
//            map.put("access_token", access_token);
////          map.put("roleIds", roleIds);
//            JSONObject json=new JSONObject();
//            json.put("interfaceCode",interfacecode);
//            json.put("appInfo", appInfo);
//            json.put("params", JSONObject.parse(params));
//            map.put("params",json);
//            String result=HttpClientUtil.get(url, map);
//            return result;
//            
//        }catch (ClientProtocolException e) {
//            throw new  BaseException(e.getMessage());
//        } catch (IOException e) {
//            throw new  BaseException(e.getMessage());
//        } catch (Exception e) {
//            throw new  BaseException(e.getMessage());
//        }
//    }

    /**
     * 生成权限url
     * @param npService 服务
     * @return url
     * @throws BaseException 异常
     * @author 王冬冬  
     * @date 2017年7月19日 下午4:41:40
     */
    private String buildAuthUrl(NPService npService) throws BaseException {
        String host=npService.getServiceHost();
        String port=npService.getServicePort();
        String checkAuth=npService.getCheckAuth();
        if(StringUtils.isEmpty(host)||StringUtils.isEmpty(checkAuth)){
            throw new BaseException("权限接口主机地址或调用地址未配置");
        }
        StringBuilder builder=new StringBuilder();
        if(!host.startsWith("http://")){
            builder.append("http://");
        }
        builder.append(host);
        if(port!=null){
            builder.append(":").append(port);
        }
        String url=builder.append(checkAuth).toString();
        return url;
    }

    /**
     * 通过access_token从redis拿数据
     * @param access_token 令牌
     * @return json
     * @author 王冬冬  
     * @date 2017年7月19日 下午3:55:51
     */
    private JSONObject getDataByAccessToken(String access_token) {
        //获取access_token的值
        String value=JedisUtil.getValue(access_token);
        if(value==null){
            return returnSuccess();
        }
        if(access_token.startsWith("AT_APP_")){
            return returnAppinfo(access_token, value);   
        }else{
            return returnUserinfo(access_token, value);
        }
    }

    /**
     * 获得接口数据
     * @param servicecode 服务编码
     * @param interfacecode 接口编码
     * @return interface
     * @throws Exception 异常
     * @author 王冬冬  
     * @date 2017年7月19日 下午4:11:53
     */
    private NPInterface getInterface(String servicecode, String interfacecode) throws Exception {
        NPInterface npinterface;
        String b=null;
        String key=new StringBuilder().append(Constants.INTERFACE).append(servicecode).append("_").append(interfacecode).toString();
        if((b=JedisUtil.getValue(key))!=null){
            npinterface=JSONObject.parseObject(b, NPInterface.class);
            logger.debug("method dataAcess() get data from redis:"+"key("+key+")");
        }else{
            npinterface=interfaceService.getInterfaceByParam(servicecode,interfacecode);
            logger.debug("method dataAcess() get data from database:"+JSONObject.toJSONString(npinterface));
            if(npinterface!=null){
                JedisUtil.setex(key,Constants.SECONDS_OF_DAY,JSONObject.toJSONString(npinterface));
            }
        }
        return npinterface;
    }

    /**
     * 返回appinfo数据
     * @param access_token 令牌
     * @param value 数据值
     * @return json
     * @author 王冬冬  
     * @date 2017年7月19日 下午4:12:07
     */
    private JSONObject returnAppinfo(String access_token, String value) {
        Map<String, Object> resultMap = JSON.parseObject(value, Map.class);//转成map
        if(resultMap==null){
            return returnSuccess();
        }
        Map<String, Object> userInfo = (Map<String,Object>)resultMap.get("appInfo");//获取appInfo数据
        return returnSuccess(userInfo);
        
    }

    /**
     * 返回userInfo数据
     * @param access_token 令牌
     * @param value 数据
     * @return json
     * @author 王冬冬  
     * @date 2017年7月19日 下午4:12:25
     */
    private JSONObject returnUserinfo(String access_token, String value) {
        JedisUtil.setex(access_token,Constants.TOKEN_EXPIRE_TIME,value);//更新redis
        Map<String, Object> resultMap = JSON.parseObject(value, Map.class);//转成map
        if(resultMap==null){
            return returnSuccess();
        }
        Map<String, Object> userInfo = (Map<String,Object>)resultMap.get("userInfo");//获取userInfo数据
        return  returnSuccess(userInfo);
    }

    /**
     * 权限接口调用
     * @param serviceCode 服务编码
     * @param interfacecode 接口编码
     * @param access_token 令牌
     * @return string
     * @author 王冬冬  
     * @param params 参数
     * @param npService 服务
     * @param appInfo 应用info
     * @param userInfo 用户info
     * @date 2017年2月6日 上午9:52:52
     */
    public String checkAuth(String serviceCode,String interfacecode, String access_token,String params,NPService npService, Map<String, Object> appInfo,Map<String, Object> userInfo) throws BaseException{
        try {
            
            String url = buildAuthUrl(npService);
            Map<String, Object> map=new HashMap<String,Object>();
            map.put("access_token", access_token);
//            map.put("roleIds", roleIds);
            JSONObject json=new JSONObject();
            json.put("interfaceCode",interfacecode);
            if(appInfo!=null){
                json.put("appInfo", appInfo);
            }
            if(userInfo!=null){
                json.put("userInfo", userInfo);
            }
            json.put("params", JSONObject.parse(params));

            map.put("params",json);
            String result=HttpClientUtil.get(url, map);
            return result;
            
        }catch (ClientProtocolException e) {
            throw new  BaseException(e.getMessage());
        } catch (IOException e) {
            throw new  BaseException(e.getMessage());
        } catch (Exception e) {
            throw new  BaseException(e.getMessage());
        }
    }

    /**
     * 安全接口调用
     * @param inferfaceCode 接口编码
     * @param serviceCode 服务编码
     * @param access_token 令牌
     * @param params 参数
     * @param npService 服务
     * @return string
     * @author 王冬冬  
     * @throws BaseException 异常 
     * @date 2017年2月6日 上午9:52:35
     */
    public String checkSafe(String inferfaceCode,String serviceCode,String access_token,String params,NPService npService) throws BaseException{
        
        try {
//                List<NPService> services=iServiceService.getByServiceCode(serviceCode);
//                NPService npService=services.get(0);
            String url = buildSafeUrl(npService);
            Map<String, Object> map=new HashMap<String,Object>();
            map.put("access_token", access_token);
                
            JSONObject json=new JSONObject();
            json.put("interfaceCode",inferfaceCode);
            json.put("params", JSONObject.parse(params));
                
            map.put("params",json);
            String result = HttpClientUtil.get(url, map);
            return result;
        } catch (ClientProtocolException e) {
            throw new  BaseException(e.getMessage());
        } catch (IOException e) {
            throw new  BaseException(e.getMessage());
        } catch (Exception e) {
            throw new  BaseException(e.getMessage());
        }
    }

    /**
     * 生成安全接口url
     * @param npService 服务
     * @return string
     * @throws BaseException 异常
     * @author 王冬冬  
     * @date 2017年7月19日 下午4:42:40
     */
    private String buildSafeUrl(NPService npService) throws BaseException {
        String host=npService.getServiceHost();
        String port=npService.getServicePort();
        String checkSafe=npService.getCheckSafe();
        if(StringUtils.isEmpty(host)||StringUtils.isEmpty(checkSafe)){
            throw new BaseException("安全接口主机或调用地址未配置");
        }
        StringBuilder builder=new StringBuilder();
        if(!host.startsWith("http://")){
            builder.append("http://");
        }
        builder.append(host);
        if(port!=null){
            builder.append(":").append(port);
        }
        String url=builder.append(checkSafe).toString();
        return url;
    }
}
