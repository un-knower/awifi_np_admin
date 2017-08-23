package com.awifi.np.admin.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.constants.Constants;
import com.awifi.np.admin.controller.base.BaseController;
import com.awifi.np.admin.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Generated;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/22
 * 创建作者：卢朱娜
 * 文件名称：ToolController.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
@RestController
@RequestMapping("/admin/tool")
public class ToolController extends BaseController{


    @RequestMapping(value = "/security_code", method = RequestMethod.GET)
    public JSONObject generateSecurityCode(){
        String SecurityCode = StringUtil.getRandomString(Constants.SECURITY_CODE_LEN);
        return returnSuccess(SecurityCode);
    }



}
