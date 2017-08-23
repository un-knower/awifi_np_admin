package com.awifi.np.admin.entity;

import java.util.Date;

public class NPAdminRolePermission extends NPAdminRolePermissionKey {
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}