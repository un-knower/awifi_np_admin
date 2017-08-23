package com.awifi.np.admin.controller.platform;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPPlatformService;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.PlatformServiceImpl;
import com.awifi.np.admin.service.impl.PlatformServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/18
 * 创建作者：卢朱娜
 * 文件名称：bindServiceController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/admin/platform")
public class BindServiceController extends BaseController {

    @Autowired
    private PlatformServiceImpl platformService;
    @Autowired
    private PlatformServiceServiceImpl platformServiceService;


    @RequestMapping(value = "/{pid}/service", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listService4PlatformBind(@PathVariable("pid") Integer pid){

        try {
            NPPlatform npPlatform = platformService.getById(pid);
            if(npPlatform==null){
                return returnError(Constants.ECodeBadParam, "pid");
            }

            //所有服务
            List<NPService> serviceList = platformServiceService.getServiceList4Bind(npPlatform);
            //按顺序返回已有服务的列表
            List<NPPlatformService> bindedList = platformServiceService.getBindedList(npPlatform);

            HashMap<String, Object> retmap = new HashMap<>();
            retmap.put("serviceList", serviceList);
            retmap.put("bindedList", bindedList);

            return returnSuccess(retmap);

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    @RequestMapping(value = "/{pid}/service", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bindService(@PathVariable("pid") Integer pid, @RequestBody List<String> serviceCodeList){
        if(pid == null){
            return returnError(ECodeParam, "pid");
        }

        if(serviceCodeList==null){
            return returnError(ECodeParam, "serviceCodeList");
        }

        NPPlatform npPlatform;
        try {
            npPlatform = platformService.getById(pid);
            if(npPlatform==null){
                return returnError(Constants.ECodeBadParam, "pid");
            }

            platformServiceService.insertBind(npPlatform, serviceCodeList);

            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }

}
