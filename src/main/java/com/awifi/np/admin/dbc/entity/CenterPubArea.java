package com.awifi.np.admin.dbc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 版权所有： 爱WiFi无线运营中心
 * 创建日期：2017/2/15
 * 创建作者：卢朱娜
 * 文件名称：CenterPubArea.java
 * 版本：  v1.0
 * 功能：
 * 修改记录：
 */
public class CenterPubArea implements Serializable {
    private Long id;//主键
    private Long parentId;//父地区编号
    private String areaName;//地区名称
    private String areaFullName;//地区全名称
    private String areaType;//地区类型
    private String areaCnCode;//地区编码
    private String postCnCode;//邮政编码
    private String crmCode;//crm编码
    private Integer status;//状态
    private Date createDate;//新建时间
    private Date modifyDate;//更新时间


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaFullName() {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName) {
        this.areaFullName = areaFullName;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaCnCode() {
        return areaCnCode;
    }

    public void setAreaCnCode(String areaCnCode) {
        this.areaCnCode = areaCnCode;
    }

    public String getPostCnCode() {
        return postCnCode;
    }

    public void setPostCnCode(String postCnCode) {
        this.postCnCode = postCnCode;
    }

    public String getCrmCode() {
        return crmCode;
    }

    public void setCrmCode(String crmCode) {
        this.crmCode = crmCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
