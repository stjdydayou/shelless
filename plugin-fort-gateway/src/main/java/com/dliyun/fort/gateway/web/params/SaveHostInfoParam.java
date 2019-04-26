package com.dliyun.fort.gateway.web.params;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 23:15
 */
public class SaveHostInfoParam {

    @NotNull(message = "请选择分组")
    private Long groupId;

    @NotNull(message = "请选择认证密钥")
    private Long authId;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "请输入主机地址")
    private String hostAddress;

    @NotNull(message = "请填写端口号")
    private Integer portNumber;

    @NotBlank(message = "请选择操作系统")
    private String os;

    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
