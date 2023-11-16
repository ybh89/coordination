package com.toy.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final HttpStatus httpStatus;

    public ApiException(ExceptionCode exceptionCode, HttpStatus httpStatus) {
        super(exceptionCode.message());
        this.exceptionCode = exceptionCode;
        this.httpStatus = httpStatus;
    }

    public ApiException(ExceptionCode exceptionCode, HttpStatus httpStatus, Throwable cause)
    {
        super(exceptionCode.message(), cause);
        this.exceptionCode = exceptionCode;
        this.httpStatus = httpStatus;
    }

    public int code() {
        return exceptionCode.code();
    }
}
