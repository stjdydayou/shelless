package com.axungu.webshell.core.vo;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/23 22:58
 */
public class HostInfoVO {

    private Long groupId;

    private Long authId;

    private String name;

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
}
