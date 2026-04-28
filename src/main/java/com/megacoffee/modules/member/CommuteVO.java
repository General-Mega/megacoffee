package com.megacoffee.modules.member;

public class CommuteVO {
    private Long seq;
    private String createDatetime;
    private String headerDetails;
    private String macAddress;
    private String os;
    private String remoteAddress;
    private String userAgent;
    private String sessionId;
    private String resultCode;
    private Long userSeq;

    public Long getSeq() {
        return seq;
    }
    public void setSeq(Long seq) {
        this.seq = seq;
    }
    public String getCreateDatetime() {
        return createDatetime;
    }
    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
    public String getHeaderDetails() {
        return headerDetails;
    }
    public void setHeaderDetails(String headerDetails) {
        this.headerDetails = headerDetails;
    }
    public String getMacAddress() {
        return macAddress;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getRemoteAddress() {
        return remoteAddress;
    }
    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;   
    }
    public Long getUserSeq() {
        return userSeq;
    }
    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

}