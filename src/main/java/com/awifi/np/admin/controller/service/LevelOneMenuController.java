package com.awifi.np.admin.controller.service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPPage;
import com.awifi.np.admin.entity.NPPlatform;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/4
 * 创建作者：卢朱娜
 * 文件名称：LevelOneMenuController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/levelonemenu")
public class LevelOneMenuController extends BaseController{

    @Autowired
    private ServiceServiceImpl serviceService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject listLevelOneMenu(NPPage page,
                                       @RequestParam(value = "keyword", required = false) String keyword,
                                       @RequestParam(value = "isMenu", required = false) Boolean isMenu){

        try {
            List<NPService> list = serviceService.listPageLevelOneMenu(page,keyword,isMenu);
            HashMap<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("list", list);
            retMap.put("totalPage", page.getTotalPage());
            retMap.put("totalRecord", page.getTotalRecord());
            retMap.put("page", page.getPage());
            retMap.put("pageSize", page.getPageSize());
            retMap.put("keyword", keyword);
            retMap.put("isMenu", isMenu);
            return returnSuccess(retMap);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }

    @RequestMapping(value = "/{sid}", method = RequestMethod.GET)
    public JSONObject getLevelOneMenu(@PathVariable("sid") Integer sid){
        try {
            NPService npService = serviceService.getById(sid);
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


    @RequestMapping(value = "", method = RequestMethod.PUT)
    public JSONObject updateLevelOneMenu(@RequestBody NPService npService){
        Boolean isMenu = npService.getIsMenu();


        if(npService.getId()==null){
            return returnError(ECodeParam, "id");
        }
        if(isMenu==null){
            return returnError(ECodeParam, "isMenu");
        }

        NPService newNPService = new NPService();
        newNPService.setId(npService.getId());
        newNPService.setIsMenu(false);

        if(isMenu){
            if(StringUtil.isEmpty(npService.getMenuName())){
                return returnError(ECodeParam, "menuName");
            }
            if(StringUtil.isEmpty(npService.getMenuUrl())){
                return returnError(ECodeParam, "menuUrl");
            }
            if(npService.getHasSubmenu()==null){
                return returnError(ECodeParam, "hasChildmenu");
            }
            if(StringUtil.isEmpty(npService.getTargetId())){
                return returnError(ECodeParam, "targetId");
            }
            if(StringUtil.isEmpty(npService.getMenuTreeApi())){
                return returnError(ECodeParam, "menuTreeApi");
            }
            if(StringUtil.isEmpty(npService.getRoleMenuApi())){
                return returnError(ECodeParam, "roleMenuApi");
            }


            if(npService.getMenuName().length()>30){
                return returnError("E1012004");
            }
            if(npService.getTargetId().length()>30){
                return returnError("E1012005");
            }

            newNPService.setIsMenu(true);
            newNPService.setMenuName(npService.getMenuName());
            newNPService.setMenuUrl(npService.getMenuUrl());
            newNPService.setHasSubmenu(npService.getHasSubmenu());
            newNPService.setTargetId(npService.getTargetId());
            newNPService.setRemark(npService.getRemark());
            newNPService.setMenuTreeApi(npService.getMenuTreeApi());
            newNPService.setRoleMenuApi(npService.getRoleMenuApi());
        }


        try {

            NPService tmpservice = serviceService.getById(newNPService.getId());
            if(tmpservice==null){
                return returnError(ECodeBadParam, "id");
            }

            serviceService.updateById(newNPService);

            return returnSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


}
