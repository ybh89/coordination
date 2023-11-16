package com.toy.common.exception;

public class DomainException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public DomainException(ExceptionCode exceptionCode) {
        super(exceptionCode.message());
        this.exceptionCode = exceptionCode;
    }

    public DomainException(ExceptionCode exceptionCode, String append) {
        super(exceptionCode.message() + "\n" + append);
        this.exceptionCode = exceptionCode;
    }

    public DomainException(ExceptionCode exceptionCode, Throwable cause) {
        super(exceptionCode.message(), cause);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode code() {
        return exceptionCode;
    }
}
