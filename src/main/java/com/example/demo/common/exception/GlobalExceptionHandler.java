package com.example.demo.common.exception;

import com.example.demo.common.response.CustomApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public CustomApiResponse<Void> handleUserException(MemberException e) {
        log.info("UserException: {}", e.getMessage());
        return CustomApiResponse.fail(e.getResponseCode(), null);
    }
}
