package com.awifi.np.admin.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPInterface;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/24
 * 创建作者：卢朱娜
 * 文件名称：InterfaceController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/interface")
public class InterfaceController extends BaseController{

    @Autowired
    private InterfaceServiceImpl interfaceService;
    @Autowired
    private ServiceServiceImpl serviceService;



    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject listInterface(NPPage page,
                                    @RequestParam(value = "keyword",required = false) String keyword,
                                    @RequestParam(value = "serviceName",required = false) String serviceName){
        try{
            List<NPInterface> list = interfaceService.listPageInterface(page, keyword, serviceName);
            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", list);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            retMap.put("serviceName", serviceName);

            return returnSuccess(retMap);

        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }


    @RequestMapping(value = "{iid}", method = RequestMethod.GET)
    public JSONObject getInterface(@PathVariable("iid") Integer iid){
        try {
            NPInterface npInterface = interfaceService.getByKey(iid);
            if(npInterface==null){
                return returnError(ECodeBadParam, "iid");
            }else {
                return returnSuccess(npInterface);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);

        }
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject addInterface(@RequestBody NPInterface npInterface, HttpServletRequest request){
        NPAdminUser user = SessionUtil.getSessionUser(request);
        String method = npInterface.getInterfaceMethod().toUpperCase();
        String url = npInterface.getInterfaceUrl();

        if(npInterface.getServiceId()==null){
            return returnError(ECodeParam, "serviceId");
        }
        if(StringUtil.isEmpty(url)){
            return returnError(ECodeParam, "interfaceUrl");
        }
        if(StringUtil.isEmpty(method)){
            return returnError(ECodeParam, "interfaceMethod");
        }
        if(StringUtil.isEmpty(npInterface.getInterfaceName())){
            return returnError(ECodeParam, "interfaceName");
        }
        if(npInterface.getIfcheck()==null){
            return returnError(ECodeParam, "ifCheck");
        }

        if(!method.equals("GET") && !method.equals("POST") && !method.equals("PUT") && !method.equals("DELETE")){
            return returnError(ECodeBadParam, "interfaceMethod");
        }


        try {
            NPService npService = serviceService.getById(npInterface.getServiceId());
            if(npService==null){
                return returnError(ECodeBadParam, "serviceId");
            }
            npInterface.setInterfaceMethod(method);
            String interfaceCode = url+":"+method;
            if(interfaceCode.length()>130){
                return returnError("E1013002");
            }
            npInterface.setInterfaceCode(interfaceCode);
            npInterface.setServiceCode(npService.getServiceCode());


            String code = interfaceService.insert(npInterface, user);
            if(code.equals(Constants.ECodeSuccess)){
                return returnSuccess();
            }else{
                return returnError(code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }



    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONObject updateInterface(@RequestBody NPInterface npInterface, HttpServletRequest request){
        NPAdminUser user = SessionUtil.getSessionUser(request);
        String method = npInterface.getInterfaceMethod().toUpperCase();
        String url = npInterface.getInterfaceUrl();

        if(npInterface.getId()==null){
            return returnError(ECodeParam, "id");
        }
        if(npInterface.getServiceId()==null){
            return returnError(ECodeParam, "serviceId");
        }
        if(StringUtil.isEmpty(url)){
            return returnError(ECodeParam, "interfaceUrl");
        }
        if(StringUtil.isEmpty(method)){
            return returnError(ECodeParam, "interfaceMethod");
        }
        if(StringUtil.isEmpty(npInterface.getInterfaceName())){
            return returnError(ECodeParam, "interfaceName");
        }
        if(npInterface.getIfcheck()==null){
            return returnError(ECodeParam, "ifCheck");
        }

        if(!method.equals("GET") && !method.equals("POST") && !method.equals("PUT") && !method.equals("DELETE")){
            return returnError(ECodeBadParam, "interfaceMethod");
        }

        try {
            NPService npService = serviceService.getById(npInterface.getServiceId());
            if(npService==null){
                return returnError(Constants.ECodeBadParam, "serviceId");
            }
            npInterface.setServiceCode(npService.getServiceCode());
            npInterface.setInterfaceMethod(method);
            String interfaceCode = url+":"+method;
            if(interfaceCode.length()>130){
                return returnError("E1013002");
            }
            npInterface.setInterfaceCode(interfaceCode);


            String code = interfaceService.update(npInterface, user);
            if(code.equals(Constants.ECodeSuccess)){
                JedisUtil.del(new StringBuilder().append(Constants.INTERFACE).append(npInterface.getServiceCode()).append("_").append(npInterface.getInterfaceCode()).toString());
                return returnSuccess();
            }else{
                return returnError(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    @RequestMapping(value = "/{iid}", method = RequestMethod.DELETE)
    public JSONObject deleteInterface(@PathVariable("iid") Integer iid){

        try {
            interfaceService.delete(iid);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }
}
