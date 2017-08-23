package com.awifi.np.admin.dto;

import java.util.HashMap;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/18
 * 创建作者：卢朱娜
 * 文件名称：InterfaceRegisterDTO.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class InterfaceRegisterDTO {
    String serviceCode;
    String token;
    Long timestamp;
    HashMap<String, InterfaceDTO> interfaceMap;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, InterfaceDTO> getInterfaceMap() {
        return interfaceMap;
    }

    public void setInterfaceMap(HashMap<String, InterfaceDTO> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }
}
