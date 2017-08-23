package com.awifi.np.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.IBizRoleInterfaceService;
import com.awifi.np.admin.utils.HttpClientUtil;
import com.awifi.np.admin.utils.NPAdminUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/23
 * 创建作者：卢朱娜
 * 文件名称：RoleInterfaceServiceImpl.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Service
public class BizRoleInterfaceServiceImpl implements IBizRoleInterfaceService {

    @Autowired
    private InterfaceServiceImpl interfaceService;


    public String replaceUrl(String url, Long rid){
        return url.replaceAll("\\{[^}]*\\}", String.valueOf(rid));
    }

    public JSONObject getRoleInterfaceRelation(Long rid, NPService npService) throws Exception {

        List<NPInterface> allInterface = interfaceService.listRegistedByServiceCode(npService.getServiceCode());

        String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());

        url = url + replaceUrl(npService.getRolePermissionApi(), rid);
        HashMap<String, Object> parammap = new HashMap<>();
        Long timestamp = new Date().getTime();
        parammap.put("timestamp", timestamp);
        parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));
        parammap.put("roleid", rid);

        String apiResult = HttpClientUtil.get(url, parammap);

        List<String> okCodeList=null;
        JSONObject apiJobj = JSONObject.parseObject(apiResult);
        if (apiJobj.getString("code").equals(Constants.ECodeSuccess)) {
            okCodeList = JSONObject.parseArray(apiJobj.getString("data"), String.class);
        } else {
            return BaseController.returnError("E1020002", apiResult);
        }

        for(NPInterface npInterface : allInterface){
            npInterface.setFlag(okCodeList.indexOf(npInterface.getInterfaceCode())>=0);
        }
        return BaseController.returnSuccess(allInterface);
    }


    public JSONObject bindRoleInterface(Long rid, NPService npService, List<String> interfaceCodeList) throws Exception{

        String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());

        url = url + replaceUrl(npService.getRolePermissionApi(), rid);

        HashMap<String, Object> parammap = new HashMap<>();
        Long timestamp = new Date().getTime();
        parammap.put("timestamp", timestamp);
        parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));
        parammap.put("roleid", rid);


        String apiresult = HttpClientUtil.post(url, parammap, JSONObject.toJSONString(interfaceCodeList));
        if(apiresult==null){
            return BaseController.returnError("E1000013","POST_url:"+url);
        }


        JSONObject apiJobj = JSONObject.parseObject(apiresult);
        if(apiJobj.getString("code").equals(Constants.ECodeSuccess)){
            return BaseController.returnSuccess();
        }else{
            return BaseController.returnError("E1020002", apiresult);
        }

    }


    public JSONObject bindRoleMenu(Long rid, NPService npService, List<Long> menuIdList) throws Exception{
        String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());

        url = url + replaceUrl(npService.getRoleMenuApi(), rid);

        HashMap<String, Object> parammap = new HashMap<>();
        Long timestamp = new Date().getTime();
        parammap.put("timestamp", timestamp);
        parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));
        parammap.put("roleid", rid);


        String apiresult = HttpClientUtil.post(url, parammap, JSONObject.toJSONString(menuIdList));
        if(apiresult==null){
            return BaseController.returnError("E1000013","POST_url:"+url);
        }

        JSONObject apiJobj = JSONObject.parseObject(apiresult);
        if(apiJobj.getString("code").equals(Constants.ECodeSuccess)){
            return BaseController.returnSuccess();
        }else{
            return BaseController.returnError("E1021002", apiresult);
        }

    }


    public JSONObject getMeuTree(NPService npService) throws Exception {
        String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());

        url = url + npService.getMenuTreeApi();

        HashMap<String, Object> parammap = new HashMap<>();
        Long timestamp = new Date().getTime();
        parammap.put("timestamp", timestamp);
        parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));

        String apiResult = HttpClientUtil.get(url, parammap);
        if(apiResult==null){
            return BaseController.returnError("E1000013","GET_url:"+url);
        }

        return JSONObject.parseObject(apiResult);

    }


    public JSONObject getMenuTreeOfBizRole(Long rid, NPService npService) throws Exception {
        String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());

        url = url + replaceUrl(npService.getRoleMenuApi(), rid);

        HashMap<String, Object> parammap = new HashMap<>();
        Long timestamp = new Date().getTime();
        parammap.put("timestamp", timestamp);
        parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));
        parammap.put("roleid", rid);

        String apiResult = HttpClientUtil.get(url, parammap);
        if(apiResult==null){
            return BaseController.returnError("E1000013","GET_url:"+url);
        }

        return JSONObject.parseObject(apiResult);
    }

	/**
	 * 获取角色相关的接口
	 * @param rid
	 * @param list
	 * @return
	 * @throws Exception
	 * @author 王冬冬  
	 * @date 2017年4月14日 下午3:41:29
	 */
	public JSONObject getRoleInterfaceRelation(Long rid, List<NPService> list) throws Exception {
		 
		List<Map<String,Object>> bizList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> interfaceList=new ArrayList<Map<String,Object>>();
		for(NPService npService:list){
		    String url = NPAdminUtil.getHost(npService.getServiceHost(), npService.getServicePort());
		    url = url + replaceUrl(npService.getRolePermissionApi(), rid);
		    HashMap<String, Object> parammap = new HashMap<>();
		    Long timestamp = new Date().getTime();
		    parammap.put("timestamp", timestamp);
		    parammap.put("token", NPAdminUtil.getToken(npService.getServiceCode(), npService.getServiceKey(), timestamp));
		    parammap.put("roleid", rid);
	
		    String apiResult = HttpClientUtil.get(url, parammap);
	
		    List<String> okCodeList=null;
		    JSONObject apiJobj = JSONObject.parseObject(apiResult);
		    if (apiJobj.getString("code").equals(Constants.ECodeSuccess)) {
		         okCodeList = JSONObject.parseArray(apiJobj.getString("data"), String.class);
		     } else {
		        return BaseController.returnError("E1020002", apiResult);
		    }
		    
		    List<NPInterface> allInterface = interfaceService.listRegistedByServiceCode(npService.getServiceCode(),okCodeList);
//		    List<NPInterface> allInterface =mockInterface(npService.getServiceCode());
		    interfaceList.clear();
		    for(NPInterface npInterface : allInterface){
		    	Map<String,Object> map=new HashMap<>();
		    	map.put("id", npInterface.getId());
		    	map.put("code", npInterface.getInterfaceCode());
		    	map.put("name", npInterface.getInterfaceName());
		    	interfaceList.add(map);
		    }
		    Map<String,Object> bizMap=new HashMap<String,Object>(); 
		    bizMap.put("id", npService.getId());
		    bizMap.put("serviceCode", npService.getServiceCode());
		    bizMap.put("menuName", npService.getMenuName());
		    bizMap.put("permissions",interfaceList);
		    bizList.add(bizMap);
		   }
		   
		   return  BaseController.returnSuccess(bizList);
	}

//	private List<NPInterface> mockInterface(String serviceCode) {
//		List<NPInterface> list=new ArrayList<>();
//		NPInterface in=new NPInterface();
//		in.setId(1237);
//		in.setInterfaceName("登录页面—登录接口");
//		in.setInterfaceCode("/pubsrv/login:POST");
//		in.setServiceCode(serviceCode);
//		list.add(in);
//		return list;
//	}
}
