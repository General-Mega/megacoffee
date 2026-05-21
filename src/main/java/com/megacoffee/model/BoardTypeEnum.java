package com.megacoffee.model;

public enum BoardTypeEnum {
    NOTICE("공지사항", "0001"),
    FAQ("자주 묻는 질문", "0002"),
    QNA("1:1 문의", "0003"),
    GENERAL("일반 게시판", "0004"),
    COMMUNITY("커뮤니티", "0005");

    private String description;
    private String code;

    BoardTypeEnum(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static BoardTypeEnum fromCode(String code) {
        if (code == null) {
            return null;
        }

        for (BoardTypeEnum type : values()) {
            if (type.code.equals(code) || type.name().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }
}