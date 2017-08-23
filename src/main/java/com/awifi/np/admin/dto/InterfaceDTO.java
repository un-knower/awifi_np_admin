package com.awifi.np.admin.dto;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/1/16
 * 创建作者：卢朱娜
 * 文件名称：InterfaceDTO.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class InterfaceDTO {
    String name;      //方法
    String type;        //调用方法
    String path;        //url
    String remark;
    Boolean ifcheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIfcheck() {
        return ifcheck;
    }

    public void setIfcheck(Boolean ifcheck) {
        this.ifcheck = ifcheck;
    }

}
