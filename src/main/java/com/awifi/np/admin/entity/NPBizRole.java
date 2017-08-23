package com.awifi.np.admin.entity;

import java.util.Date;
import java.util.List;

public class NPBizRole {
    private Long id;

    private String roleName;

    private Date createDate;

    private Date updateDate;

    private Long createUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    private List<String> suitCodeList;

    public List<String> getSuitCodeList() {
        return suitCodeList;
    }

    public void setSuitCodeList(List<String> suitCodeList) {
        this.suitCodeList = suitCodeList;
    }
}