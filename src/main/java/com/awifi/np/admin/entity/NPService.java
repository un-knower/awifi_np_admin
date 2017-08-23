package com.awifi.np.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NPService implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String serviceCode;

    private String serviceKey;

    private String serviceName;

    private String serviceHost;

    private Integer createUserId;

    private String createUsername;

    private String checkAuth;

    private String servicePort;

    private Byte publishStatus;

    private String version;

    private Boolean isMenu;

    private Boolean hasSubmenu;

    private String menuUrl;

    private String menuName;

    private String targetId;

    private String templateCrud;

    private String checkSafe;

    private String roleMenuApi;

    private String rolePermissionApi;

    private String menuTreeApi;

    private String remark;

    private Date createDate;

    private List<NPInterface> interfaces;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCheckAuth() {
        return checkAuth;
    }

    public void setCheckAuth(String checkAuth) {
        this.checkAuth = checkAuth;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public Byte getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Byte publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public Boolean getHasSubmenu() {
        return hasSubmenu;
    }

    public void setHasSubmenu(Boolean hasSubmenu) {
        this.hasSubmenu = hasSubmenu;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTemplateCrud() {
        return templateCrud;
    }

    public void setTemplateCrud(String templateCrud) {
        this.templateCrud = templateCrud;
    }

    public String getCheckSafe() {
        return checkSafe;
    }

    public void setCheckSafe(String checkSafe) {
        this.checkSafe = checkSafe;
    }

    public String getRoleMenuApi() {
        return roleMenuApi;
    }

    public void setRoleMenuApi(String roleMenuApi) {
        this.roleMenuApi = roleMenuApi;
    }

    public String getRolePermissionApi() {
        return rolePermissionApi;
    }

    public void setRolePermissionApi(String rolePermissionApi) {
        this.rolePermissionApi = rolePermissionApi;
    }

    public String getMenuTreeApi() {
        return menuTreeApi;
    }

    public void setMenuTreeApi(String menuTreeApi) {
        this.menuTreeApi = menuTreeApi;
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
    private Boolean isBinded;

    private Boolean isOwned;

    public Boolean getBinded() {
        return isBinded;
    }

    public void setBinded(Boolean binded) {
        isBinded = binded;
    }

    public Boolean getOwned() {
        return isOwned;
    }

    public void setOwned(Boolean owned) {
        isOwned = owned;
    }

    private String platformName; 

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	public List<NPInterface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<NPInterface> interfaces) {
		this.interfaces = interfaces;
	}
}