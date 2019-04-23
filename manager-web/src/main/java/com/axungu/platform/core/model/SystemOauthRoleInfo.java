package com.axungu.platform.core.model;

import java.io.Serializable;

/**
 * @author jtoms
 */
public class SystemOauthRoleInfo implements Serializable {

    private static final long serialVersionUID = 5241138963805332125L;

    private Long id;

    private String name;

    private String remark;

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
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}