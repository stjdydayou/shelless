package com.dliyun.fort.gateway.core.model;

import java.util.Date;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:20
 */
public class HostInfo {

    private Long id;

    private Long groupId;

    private Long authId;

    private String name;

    private String hostAddress;

    private Integer portNumber;

    private String os;

    private String remark;

    private Date createdTime;


    //非数据库字段

    private HostGroup hostGroup;

    private HostAuth hostAuth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HostGroup getHostGroup() {
        return hostGroup;
    }

    public void setHostGroup(HostGroup hostGroup) {
        this.hostGroup = hostGroup;
    }

    public HostAuth getHostAuth() {
        return hostAuth;
    }

    public void setHostAuth(HostAuth hostAuth) {
        this.hostAuth = hostAuth;
    }
}
