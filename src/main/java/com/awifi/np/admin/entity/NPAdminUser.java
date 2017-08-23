package com.awifi.np.admin.entity;

import java.io.Serializable;
import java.util.Date;

public class NPAdminUser implements Serializable{
    
    /** 对象序列化 */
    private static final long serialVersionUID = -1235237807111706335L;

    private Integer id;

    private String loginAccount;

    private String loginPwd;

    private Date createDate;

    private Date lastLoginDate;

    private Integer errorCount;

    private Integer state;

    private Integer roleId;

    private String phone;

    private String remark;
    private NPAdminRole role;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public NPAdminRole getRole() {
		return role;
	}

	public void setRole(NPAdminRole role) {
		this.role = role;
	}
}