package com.megacoffee.modules.system.user;

import com.megacoffee.model.SearchVO;

public class SystemUserSearchVO extends SearchVO {
    private Long authSeq;

    public Long getAuthSeq() {
        return authSeq;
    }

    public void setAuthSeq(Long authSeq) {
        this.authSeq = authSeq;
    }
}