package com.awifi.np.admin.entity;

import java.util.Date;

public class NPServicePublishLog {
    private Integer id;

    private Integer serviceId;

    private String serviceCode;

    private String publishVersion;

    private Byte publishStatus;

    private Date configDate;

    private Date testDate;

    private Date checkDate;

    private Date publishDate;

    private Date cancelDate;

    private Integer configUserId;

    private Integer testUserId;

    private Integer checkUserId;

    private Integer publishUserId;

    private Integer cancelUserId;

    private String config;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode == null ? null : serviceCode.trim();
    }

    public String getPublishVersion() {
        return publishVersion;
    }

    public void setPublishVersion(String publishVersion) {
        this.publishVersion = publishVersion == null ? null : publishVersion.trim();
    }

    public Byte getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Byte publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Date getConfigDate() {
        return configDate;
    }

    public void setConfigDate(Date configDate) {
        this.configDate = configDate;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Integer getConfigUserId() {
        return configUserId;
    }

    public void setConfigUserId(Integer configUserId) {
        this.configUserId = configUserId;
    }

    public Integer getTestUserId() {
        return testUserId;
    }

    public void setTestUserId(Integer testUserId) {
        this.testUserId = testUserId;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Integer getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Integer publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Integer getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Integer cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public NPService npService;

    public NPService getNpService() {
        return npService;
    }

    public void setNpService(NPService npService) {
        this.npService = npService;
    }
}