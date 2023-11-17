package com.toy.common.exception;

public enum ExceptionCode {
    BRAND_NAME_NOT_EXIST(100000, "브랜드 이름이 존재하지 않습니다."),
    BRAND_NOT_EXIST(100004, "해당 브랜드가 존재하지 않습니다."),

    PRODUCT_PRICE_IS_NEGATIVE(200000, "상품 가격은 음수가 될 수 없습니다."),
    BRAND_ID_IS_NULL(200001, "브랜드 아이디가 null 입니다."),
    CATEGORY_VALUE_IS_INVALID(200002, "카테고리 값이 유효하지 않습니다."),
    PRODUCT_NOT_EXIST(200003, "해당 상품이 존재하지 않습니다.")
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
