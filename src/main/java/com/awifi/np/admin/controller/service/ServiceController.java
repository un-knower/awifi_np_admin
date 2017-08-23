package com.awifi.np.admin.controller.service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.PlatformServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.redis.JedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/22
 * 创建作者：卢朱娜
 * 文件名称：ServiceController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/admin/service")
//@RequestMapping("/admin/service")
public class ServiceController extends BaseController{
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private PlatformServiceImpl platformService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listService(NPPage page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "isOnline", required = false) Boolean isOnline,
                                  @RequestParam(value = "isMenu", required = false) Boolean isMenu){



        try {
            //获取所有
            if(page.getPageSize()<0){
                List<NPService> list = serviceService.listAll();
                return returnSuccess(list);
            }

            //获取所有已上线的服务
            if(isOnline!=null && isOnline){
                List<NPService> list = serviceService.listOnlineService(isMenu);
                return returnSuccess(list);

            }


            List<NPService> list = serviceService.listPageService(page,keyword);
            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", list);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            return returnSuccess(retMap);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }

    @RequestMapping(value = "/{sid}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getService(@PathVariable("sid") Integer sid) {

        try {
            NPService npService = serviceService.getJoinInfo(sid);
            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            } else {
                return returnSuccess(npService);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addService(@RequestBody NPService npService, HttpServletRequest request){

        NPAdminUser adminUser = SessionUtil.getSessionUser(request);

        if(StringUtil.isEmpty(npService.getServiceCode())){
            return returnError(ECodeParam, "serviceCode");
        }

        if(StringUtil.isEmpty(npService.getServiceKey())){
            return returnError(ECodeParam, "serviceKey");
        }

        if(npService.getPid()==null){
            return returnError(ECodeParam, "pid");
        }

        if(npService.getServiceCode().length()>20){
            return returnError("E1012003");
        }

        try {
            NPPlatform npPlatform = platformService.getById(npService.getPid());
            if(npPlatform==null){
                return returnError(ECodeBadParam, "pid");
            }

            List<NPService> serviceList = serviceService.getByServiceCode(npService.getServiceCode());
            if(serviceList.size()!=0){
                return returnError("E1012001");
            }

            serviceService.insert(npService, npPlatform, adminUser);

            return returnSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateService(@RequestBody NPService npService) {

        if(npService.getId()==null){
            return returnError(ECodeParam, "id");
        }

        if(StringUtil.isEmpty(npService.getServiceCode())){
            return returnError(ECodeParam, "serviceCode");
        }

        if(StringUtil.isEmpty(npService.getServiceKey())){
            return returnError(ECodeParam, "serviceKey");
        }

        if(npService.getPid()==null){
            return returnError(ECodeParam, "pid");
        }

        if(npService.getServiceCode().length()>20){
            return returnError("E1012003");
        }

        try {

            NPService tmpservice = serviceService.getById(npService.getId());
            if(tmpservice==null){
                return returnError(ECodeBadParam, "id");
            }

            NPPlatform npPlatform = platformService.getById(npService.getPid());
            if(npPlatform==null){
                return returnError(ECodeBadParam, "pid");
            }

            //不是未上线的服务，不能修改serviceCode
            if(tmpservice.getPublishStatus()!=Constants.SERVICR_NOT_ON_LINE
                    && !npService.getServiceCode().equals(tmpservice.getServiceCode())){
                return returnError("E1012002");
            }

            String code = serviceService.updateAndBindPlatform(npService, npPlatform);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }
    }

    @RequestMapping(value = "/biz", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getServiceByRoleId(){

        try {
        	String serviceCode="S_PUB";
        	List<String> servcieCodess=new ArrayList<>();
        	servcieCodess.add(serviceCode);
			List<NPService> service=serviceService.getByServiceCodes(servcieCodess);
			return returnSuccess(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}
