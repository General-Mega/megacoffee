package com.megacoffee.modules.user;

import java.time.LocalDateTime;

import com.megacoffee.model.BaseVO;

/**
 * 사용자 정보 VO
 * @author user
 */
public class UserVO extends BaseVO{
    private Long seq;
    private String userId;
    private String password;
    private boolean passwordReset = false;
    private LocalDateTime lastLoginDatetime;
    
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
    public LocalDateTime getLastLoginDatetime() {
        return lastLoginDatetime;
    }
    public void setLastLoginDatetime(LocalDateTime lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    
}