package com.megacoffee.modules.system.user;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.megacoffee.model.BaseVO;

/**
 * 사용자 정보 VO
 * @author user
 */
public class SystemUserVO extends BaseVO{
    private Long seq;
    private String userId;
    private String name;
    private String mobile;
    private String email;
    private String password;
    private boolean passwordReset = false;
    private Long authSeq;
    private String authName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDate;
    public Long getSeq() {
        return seq;
    }
    public void setSeq(Long seq) {
        this.seq = seq;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isPasswordReset() {
        return passwordReset;
    }
    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }
    public Long getAuthSeq() {
        return authSeq;
    }
    public void setAuthSeq(Long authSeq) {
        this.authSeq = authSeq;
    }
    public String getAuthName() {
        return authName;
    }
    public void setAuthName(String authName) {
        this.authName = authName;
    }
    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }
    public void setLastLoginDate(LocalDateTime lastLoginDatetime) {
        this.lastLoginDate = lastLoginDatetime;
    }

    
}