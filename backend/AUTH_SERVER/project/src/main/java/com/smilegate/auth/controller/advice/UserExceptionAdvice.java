package com.smilegate.auth.controller.advice;

import com.smilegate.auth.dto.ResultResponse;
import com.smilegate.auth.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultResponse handleAdminAuthNeeded(Exception e) {
        return ResultResponse.builder()
                            .success("false")
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message(e.getMessage())
                            .build();
    }

    @ResponseBody
    @ExceptionHandler(SignoutException.class)
    public ResultResponse handelSignoutException(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(EmailNotExistException.class)
    public ResultResponse handleEmailNotExist(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ExistEmailException.class)
    public ResultResponse handleExistEmail(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResultResponse handleExpiredRefreshToken(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    public ResultResponse handleInvalidToken(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(LoginNeededException.class)
    public ResultResponse handleLoginNeeded(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(PasswordWrongException.class)
    public ResultResponse handlePasswordWrong(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationEntryPointException.class)
    public ResultResponse handleAuthenticationEntryPoint(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResultResponse handleAccessDenied(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

}
