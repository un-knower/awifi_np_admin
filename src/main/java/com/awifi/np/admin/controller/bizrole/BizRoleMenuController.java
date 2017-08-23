package com.awifi.np.admin.controller.bizrole;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPBizRole;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.BizRoleInterfaceServiceImpl;
import com.awifi.np.admin.service.impl.BizRoleServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.StringUtil;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/23
 * 创建作者：卢朱娜
 * 文件名称：BizRoleMenuController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("admin")
public class BizRoleMenuController extends BaseController {
    @Autowired
    private BizRoleServiceImpl bizRoleService;
    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private BizRoleInterfaceServiceImpl bizRoleInterfaceService;

    @RequestMapping(value = "/bizrole/{rid}/service/{sid}/menu", method = RequestMethod.PUT)
    public JSONObject bindBizRoleMenu (@PathVariable("rid") Long rid,
                                    @PathVariable("sid") Integer sid,
                                    @RequestBody List<Long> menuIdList){
        NPService npService = null;
        NPBizRole npBizRole = null;

        try {
            npBizRole = bizRoleService.getById(rid);
            if (npBizRole == null) {
                return returnError(Constants.ECodeBadParam, "rid");
            }
            npService = serviceService.getById(sid);

            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            }

            if(!npService.getIsMenu()){
                return returnError("E1021003");
            }

            if (StringUtil.isEmpty(npService.getRoleMenuApi())) {
                return returnError("E1021001");
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

        try{

            return bizRoleInterfaceService.bindRoleMenu(rid, npService, menuIdList);

//        }catch (ClientProtocolException ce) {
//            ce.printStackTrace();
//            return returnError("E1021002", npService.getServiceCode()+" ClientProtocolException "+ce.getMessage());
//        }catch(UnknownHostException ue) {
//            ue.printStackTrace();
//            return returnError("E1021002", npService.getServiceCode()+" UnknownHostException:"+ue.getMessage());
//        }catch (com.alibaba.fastjson.JSONException je) {
//            je.printStackTrace();
//            return returnError("E1021002", npService.getServiceCode() + " 返回的内容不是json串");
//        }catch (org.apache.http.conn.ConnectTimeoutException te){
//            te.printStackTrace();
//            return returnError("E1021002", npService.getServiceCode() + " ConnectTimeoutException");
        }catch (Exception e){
            e.printStackTrace();
            return returnError("E1021002", npService.getServiceCode() + e.getClass().getName());
        }

    }


    @RequestMapping(value = "/service/{sid}/menu", method = RequestMethod.GET)
    public JSONObject getInterfacesTree(@PathVariable("sid") Integer sid){
        NPService npService = null;

        try {

            npService = serviceService.getById(sid);

            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            }

            if (!npService.getIsMenu()) {
                return returnError("E1021003");
            }

            if (StringUtil.isEmpty(npService.getMenuTreeApi())) {
                return returnError("E1021004");
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

        try{
            return bizRoleInterfaceService.getMeuTree(npService);
        } catch (Exception e) {
            e.printStackTrace();
            return returnError("E1021005", npService.getServiceCode() + e.getClass().getName());
        }

    }


    @RequestMapping(value = "/bizrole/{rid}/service/{sid}/menu", method = RequestMethod.GET)
    public JSONObject getMenuTreeOfBizRole(@PathVariable("rid") Long rid,
                                    @PathVariable("sid") Integer sid){

        NPService npService = null;
        NPBizRole npBizRole = null;
        try {
            npBizRole = bizRoleService.getById(rid);
            if (npBizRole == null) {
                return returnError(Constants.ECodeBadParam, "rid");
            }
            npService = serviceService.getById(sid);

            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            }

            if(!npService.getIsMenu()){
                return returnError("E1021003");
            }

            if (StringUtil.isEmpty(npService.getRoleMenuApi())) {
                return returnError("E1021001");
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

        try{
            return bizRoleInterfaceService.getMenuTreeOfBizRole(rid, npService);
        }catch (Exception e){
            e.printStackTrace();
            return returnError("E1021002", npService.getServiceCode() + e.getClass().getName());
        }

    }



}
