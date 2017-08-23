package com.awifi.np.admin.controller.platform;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPAdminUser;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.service.impl.PlatformServiceImpl;
import com.awifi.np.admin.utils.SessionUtil;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/11
 * 创建作者：卢朱娜
 * 文件名称：PlateformController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@Controller
@RequestMapping("/admin/platform")
public class PlateformController extends BaseController{

    @Resource
    private PlatformServiceImpl platformService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listPlatform(NPPage page,
                           @RequestParam(value = "keyword", required = false) String keyword){

        try {

            //获取所有
            if(page.getPageSize()<0){
                List<NPPlatform> list = platformService.listAll();
                return returnSuccess(list);
            }

            List<NPPlatform> list = platformService.listPagePlatform(page,keyword);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPlatform(@PathVariable("id") Integer id){

        try{
            NPPlatform npPlatform = platformService.getById(id);
            if(npPlatform==null){
                return returnError(Constants.ECodeBadParam, "id");
            }else{
                return returnSuccess(npPlatform);
            }

        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);

        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addPlatform(@RequestBody NPPlatform npPlatform, HttpServletRequest request){

        NPAdminUser user = SessionUtil.getSessionUser(request);

        if(StringUtils.isEmpty(npPlatform.getAppId())){
            return returnError(ECodeParam, "appId");
        }
        if(StringUtils.isEmpty(npPlatform.getAppKey())){
            return returnError(ECodeParam, "appKey");
        }
        if(StringUtils.isEmpty(npPlatform.getPlatformUrl())){
            return returnError(ECodeParam, "platformUrl");
        }

        if(npPlatform.getAppId().length()>10){
            return returnError("E1011003");
        }

        if(!npPlatform.getPlatformUrl().startsWith("http")){
            return returnError("E1011002");
        }

        //检查appid是否已被使用
        try {
            List<NPPlatform> list = platformService.selectByAppId(npPlatform.getAppId());
            if(list.size()>0){
                return returnError("E1011001");
            }
            platformService.insert(npPlatform, user);

            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeFailed);
        }

    }


    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updatePlatform(@RequestBody NPPlatform npPlatform){
        if(npPlatform.getId()==null){
            return returnError(ECodeParam, "id");
        }
        if(StringUtils.isEmpty(npPlatform.getAppId())){
            return returnError(ECodeParam, "appId");
        }
        if(StringUtils.isEmpty(npPlatform.getAppKey())){
            return returnError(ECodeParam, "appKey");
        }
        if(StringUtils.isEmpty(npPlatform.getPlatformUrl())){
            return returnError(ECodeParam, "platformUrl");
        }

        if(npPlatform.getAppId().length()>10){
            return returnError("E1011003");
        }

        if(!npPlatform.getPlatformUrl().startsWith("http")){
            return returnError("E1011002");
        }


        try{
            String code = platformService.update(npPlatform);
            if(code.equals(Constants.ECodeSuccess)) {
                return returnSuccess();
            }else{
                return returnError(code);
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeFailed);
        }
    }


}
