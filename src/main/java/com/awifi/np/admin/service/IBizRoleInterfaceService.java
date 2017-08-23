package com.awifi.np.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.awifi.np.admin.entity.NPService;

import java.util.List;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/23
 * 创建作者：卢朱娜
 * 文件名称：IRoleInterfaceService.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public interface IBizRoleInterfaceService {

    public JSONObject getRoleInterfaceRelation(Long rid, NPService npService) throws Exception;
    public JSONObject bindRoleInterface(Long rid, NPService npService, List<String> interfaceCodeList)throws Exception;



    }
