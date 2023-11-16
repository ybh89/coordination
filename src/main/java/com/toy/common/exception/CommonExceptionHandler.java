package com.toy.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ExceptionResponse>> handleBindException(BindException exception, Locale locale) {
        log.error("필드 에러가 발생", exception);
        List<FieldError> fieldErrors = exception.getFieldErrors();

        List<ExceptionResponse> exceptionResponses = fieldErrors.stream()
                .map(fieldError -> ExceptionResponse.builder()
                        .field(fieldError.getField())
                        .objectName(fieldError.getObjectName())
                        .rejectedValue(fieldError.getRejectedValue())
                        .message(messageSource.getMessage(fieldError, locale))
                        .dateTime(LocalDateTime.now())
                        .build())
                .toList();
        return ResponseEntity.status(BAD_REQUEST).body(exceptionResponses);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(Exception exception) {
        log.error("서버 개발자 확인이 필요", exception);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .exceptionType(exception.getClass().getTypeName())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleApiException(ApiException exception) {
        log.error("Api 에러 발생", exception);
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .code(exception.code())
                .exceptionType(exception.getClass().getTypeName())
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(exception.getHttpStatus()).body(exceptionResponse);
    }
}
