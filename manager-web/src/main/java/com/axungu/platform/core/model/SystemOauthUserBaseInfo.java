package com.axungu.platform.core.model;

import com.axungu.platform.core.enums.Gender;
import com.axungu.platform.core.enums.UserState;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * @date 2017-6-7
 */
public class UserBaseInfo implements Serializable {

    private static final long serialVersionUID = 925241835389657323L;

    private Long id;

    private String nickName;

    private String avatar;

    private Gender gender;

    private Date registerTime;

    private String registerIp;

    private UserState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp == null ? null : registerIp.trim();
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
