package com.awifi.np.admin.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.dto.InterfaceDTO;
import com.awifi.np.admin.dto.InterfaceRegisterDTO;
import com.awifi.np.admin.entity.NPInterfaceTmp;
import com.awifi.np.admin.entity.NPService;
import com.awifi.np.admin.service.impl.InterfaceTmpServiceImpl;
import com.awifi.np.admin.service.impl.ServiceServiceImpl;
import com.awifi.np.admin.utils.NPAdminUtil;
import com.awifi.np.admin.utils.StringUtil;
import com.awifi.np.admin.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：InterfaceRegisterController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/externalapi")
public class InterfaceRegisterController extends BaseController{


    @Autowired
    private ServiceServiceImpl serviceService;
    @Autowired
    private InterfaceTmpServiceImpl  interfaceTmpService;

    /**
     * 在临时表中注册接口
     * @param interfaceRegisterDTO
     * @return
     */
    @RequestMapping(value = "/interface/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody InterfaceRegisterDTO interfaceRegisterDTO){
        NPService npService;
        String serviceCode = interfaceRegisterDTO.getServiceCode();
        String token = interfaceRegisterDTO.getToken();
        Long timestamp = interfaceRegisterDTO.getTimestamp();
        HashMap<String, InterfaceDTO> interfaceMap = interfaceRegisterDTO.getInterfaceMap();


        //参数校验
        if(StringUtil.isEmpty(serviceCode)){
            return returnError(ECodeParam, "serviceCode");
        }
        if(StringUtil.isEmpty(token)){
            return returnError(ECodeParam, "token");
        }
        if(timestamp==null){
            return returnError(ECodeParam, "timestamp");
        }
        if(interfaceMap==null){
            return returnError(ECodeParam, "interfaceMap");
        }
        if(interfaceMap.keySet().size()==0){
            return returnError(ECodeParam, "keys of interfaceMap");
        }

        try {
            //查找服务
            List<NPService> serviceList =  serviceService.getByServiceCode(serviceCode);
            if(serviceList.size()==0){
                return returnError("E1015001");
            }else if (serviceList.size()>1){
                return returnError("E1015003");
            }else{
                npService = serviceList.get(0);
            }

            //token校验
            if(!NPAdminUtil.checkToken(token, npService.getServiceCode(), npService.getServiceKey(), timestamp)){
                return returnError(ECodeToken);
            }

            //获取之前配置的接口列表
            List<NPInterfaceTmp> oldInterfaceList = interfaceTmpService.listByServiceCode(serviceCode);
            //获取之前配置的接口的最大时间
            Date maxCreateDate = interfaceTmpService.getMaxDate(serviceCode);

            //补充信息(说明，是否校验)
            for(NPInterfaceTmp interfaceTmp : oldInterfaceList){
                String tmpcode = interfaceTmp.getInterfaceCode();
                InterfaceDTO interfaceDTO = interfaceMap.get(tmpcode);
                if(interfaceDTO!=null) {
                    interfaceDTO.setRemark(interfaceTmp.getRemark());
                    interfaceDTO.setIfcheck(interfaceTmp.getIfcheck());
                }
            }

            //考虑同时接受到同个请求
            Thread.sleep(Tools.randInt(1000));
            Date maxCreateDate2 = interfaceTmpService.getMaxDate(serviceCode);
            if((maxCreateDate==null && maxCreateDate2==null) ||
                     (maxCreateDate2!=null && maxCreateDate!=null && maxCreateDate2.getTime()==maxCreateDate.getTime())){
            }else{
                return returnError("E1015002");
            }

            interfaceTmpService.deleteAndInsert(serviceCode, interfaceMap);

            return returnSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            return returnError(ECodeException);
        }
    }

    @RequestMapping("/test")
    public JSONObject test(@RequestBody Object obj){
        if(obj!=null){
            return returnSuccess(obj);
        }else {
            return returnSuccess();
        }
    }


}
