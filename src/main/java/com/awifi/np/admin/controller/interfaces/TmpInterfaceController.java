package com.awifi.np.admin.controller.interfaces;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceTmpServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/8
 * 创建作者：卢朱娜
 * 文件名称：TmpInterfaceController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/service")
public class TmpInterfaceController extends BaseController{

    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private InterfaceTmpServiceImpl interfaceTmpService;


    @RequestMapping(value = "/{sid}/tmpinterface", method = RequestMethod.GET)
    public JSONObject listTmpInterfaceByService(@PathVariable("sid") Integer sid){

        try {
            NPService npService = serviceService.getById(sid);
            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            } else {
                List<NPInterfaceTmp> list = interfaceTmpService.listByServiceCode(npService.getServiceCode());
                return returnSuccess(list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }

    }


    /**
     * 批量更新临时表中的ifCheck、status
     * @return
     */
    @RequestMapping(value = "/{sid}/tmpinterface", method = RequestMethod.PUT)
    public JSONObject updateTmpInterfaceBulk(@PathVariable("sid") Integer sid,
                                             @RequestBody JSONObject jsonObject){
        List<Integer> notCheckList;
        List<Integer> notRegisterList;
        try {
            notCheckList = JSON.parseArray(jsonObject.getString("notCheckList"), Integer.class);
            notRegisterList = JSON.parseArray(jsonObject.getString("notRegisterList"), Integer.class);
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeRequestBody);
        }

        if (sid == null) {
            return returnError(ECodeParam, "sid");
        }
        if (notCheckList == null) {
            return returnError(ECodeParam, "notCheckList");
        }
        if (notRegisterList == null) {
            return returnError(ECodeParam, "notRegisterList");
        }

        try {
            NPService npService = serviceService.getById(sid);
            if (npService == null) {
                return returnError(Constants.ECodeBadParam, "sid");
            } else {
                interfaceTmpService.updateBulk(npService.getServiceCode(), notCheckList, notRegisterList);
                return returnSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            return returnError(Constants.ECodeException);
        }

    }


}
