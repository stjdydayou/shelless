package com.dliyun.fort.gateway.web.params;

import javax.validation.constraints.NotBlank;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 23:15
 */
public class SaveGroupParam {

    @NotBlank(message = "名称不能为空")
    private String name;

    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
