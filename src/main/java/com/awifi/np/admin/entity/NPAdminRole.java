package com.awifi.np.admin.entity;

import java.util.Date;
import java.util.List;

public class NPAdminRole {
    private Integer id;

    private String roleName;

    private String remark;

    private Date createDate;

    private Date updateDate;

    private List<NPAdminPermission> permissions;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

	public List<NPAdminPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<NPAdminPermission> permissions) {
		this.permissions = permissions;
	}
}