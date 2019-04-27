package com.dliyun.platform.web.params;

import com.dliyun.platform.common.validation.MobileNumber;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 21:20
 */
public class SaveUserInfoParam {
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

//    @NotBlank(message = "用户登录账户不能为空")
    private String userName;

//    @NotBlank(message = "请输入手机号")
    @MobileNumber(message = "请输入正确的手机号")
    private String mp;

    @Email(message = "请输入正确的邮箱")
    private String email;


    private SystemOauthUserBaseInfo.Gender gender;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SystemOauthUserBaseInfo.Gender getGender() {
        return gender;
    }

    public void setGender(SystemOauthUserBaseInfo.Gender gender) {
        this.gender = gender;
    }
}
