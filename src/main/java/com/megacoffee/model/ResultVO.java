package com.megacoffee.model;

public class ResultVO {
    private int code;
    private String message;
    private Object data;
    private PageVO paging;

    public ResultVO() {
        this.code = 200; // 기본적으로 성공을 나타내는 코드
        this.message = "Success"; // 기본 메시지
    }

    // Constructors
    public ResultVO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVO(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(int code, String message, Object data, PageVO paging) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.paging = paging;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageVO getPaging() {
        return paging;
    }

    public void setPaging(PageVO paging) {
        this.paging = paging;
    }
}
