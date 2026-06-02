package com.megacoffee.modules.admin.system.authorization;

import java.util.List;

public class SystemAuthorizationVO {
    private Long authSeq;
    private List<Long> seqs;

    public Long getAuthSeq() {
        return authSeq;
    }

    public void setAuthSeq(Long authSeq) {
        this.authSeq = authSeq;
    }

    public List<Long> getSeqs() {
        return seqs;
    }

    public void setSeqs(List<Long> seqs) {
        this.seqs = seqs;
    }
}
