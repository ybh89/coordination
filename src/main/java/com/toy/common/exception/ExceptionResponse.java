package com.toy.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Getter
public class ExceptionResponse {
    private final String field;
    private final Object rejectedValue;
    private final String objectName;
    private final String message;
    private final int code;
    private final String exceptionType;
    private final Exception exception;
    private final LocalDateTime dateTime;

    @Builder
    private ExceptionResponse(String field, Object rejectedValue, String objectName, String message, int code,
                              String exceptionType, Exception exception, LocalDateTime dateTime) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.objectName = objectName;
        this.message = message;
        this.code = code;
        this.exceptionType = exceptionType;
        this.exception = exception;
        this.dateTime = dateTime;
    }
}
