package com.megacoffee.model;

import java.time.LocalDateTime;

public class BaseVO {
    private LocalDateTime createDatetime;
    private Long createIdx;
    private LocalDateTime updateDatetime;
    private Long updateIdx;
    private LocalDateTime deleteDatetime;
    private Long deleteIdx;
    private boolean deleted = false;
    
    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }
    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
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