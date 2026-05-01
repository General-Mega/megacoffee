package com.megacoffee.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseVO {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDatetime;
    private String createUserName;
    private Long createIdx;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateDatetime;
    private String updateUserName;
    private Long updateIdx;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deleteDatetime;
    private Long deleteIdx;
    private boolean deleted = false;

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }
    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }
    public String getCreateUserName() {
        return createUserName;
    }
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public Long getCreateIdx() {
        return createIdx;
    }
    public void setCreateIdx(Long createIdx) {
        this.createIdx = createIdx;
    }
    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }
    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
    public String getUpdateUserName() {
        return updateUserName;
    }
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
    public Long getUpdateIdx() {
        return updateIdx;
    }
    public void setUpdateIdx(Long updateIdx) {
        this.updateIdx = updateIdx;
    }
    public LocalDateTime getDeleteDatetime() {
        return deleteDatetime;
    }
    public void setDeleteDatetime(LocalDateTime deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }
    public Long getDeleteIdx() {
        return deleteIdx;
    }
    public void setDeleteIdx(Long deleteIdx) {
        this.deleteIdx = deleteIdx;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    

    
}