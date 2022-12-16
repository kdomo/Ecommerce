package com.domo.ecommerce.exception;

import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static String getSimpleName(Exception e) {
        return e.getClass().getSimpleName();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMsg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ErrorMsg.builder()
                .msg(message)
                .errorCode(getSimpleName(e))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateMemberIdException.class)
    public ErrorMsg handleDuplicateMemberIdException(DuplicateMemberIdException e) {
        return ErrorMsg.builder()
                .msg(e.getLocalizedMessage())
                .errorCode(getSimpleName(e))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public ErrorMsg handleNotLoginException(NotLoginException e) {
        return ErrorMsg.builder()
                .msg(e.getLocalizedMessage())
                .errorCode(getSimpleName(e))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAdminLoginException.class)
    public ErrorMsg handleNotAdminLoginException(NotAdminLoginException e) {
        return ErrorMsg.builder()
                .msg(e.getLocalizedMessage())
                .errorCode(getSimpleName(e))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailException.class)
    public ErrorMsg handleLoginFailException(LoginFailException e) {
        return ErrorMsg.builder()
                .msg(e.getLocalizedMessage())
                .errorCode(getSimpleName(e))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
