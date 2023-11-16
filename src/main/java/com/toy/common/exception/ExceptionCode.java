package com.toy.common.exception;

public enum ExceptionCode {
    BRAND_NAME_NOT_EXIST(100000, "브랜드 이름이 존재하지 않습니다.")
    ;

    private int code;
    private String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
