package com.dliyun.fort.gateway.core.model;

import java.util.Date;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:19
 */
public class HostGroup {
    private Long id;
    private String name;
    private String remark;
    private Date createdTime;

    private Long hostCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getHostCount() {
        return hostCount;
    }

    public void setHostCount(Long hostCount) {
        this.hostCount = hostCount;
    }
}
