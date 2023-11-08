package com.kibong.shoppingwiki.user;

import io.jsonwebtoken.JwtException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> HandlerIllegalJwtException(JwtException e) {

        return handleExceptionInternal(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpStatus httpStatus, Integer errorCode, String message) {
        return ResponseEntity.status(httpStatus).body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(Integer errorCode, String message) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(errorCode);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(LocalDateTime.now());

        return errorResponse;
    }

    @Data
    static class ErrorResponse{
        private Integer status;
        private LocalDateTime timestamp;
        private String message;
    }
}
