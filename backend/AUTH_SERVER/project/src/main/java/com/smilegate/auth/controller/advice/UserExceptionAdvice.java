package com.smilegate.auth.controller.advice;

import com.smilegate.auth.dto.ResultResponse;
import com.smilegate.auth.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultResponse handleAdminAuthNeeded(Exception e) {
        return ResultResponse.builder()
                            .success("false")
                            .status(HttpStatus.UNAUTHORIZED.value())
                            .message(e.getMessage())
                            .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SignoutException.class)
    public ResultResponse handelBlackListToken(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotExistException.class)
    public ResultResponse handleEmailNotExist(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistEmailException.class)
    public ResultResponse handleExistEmail(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResultResponse handleExpiredRefreshToken(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ResultResponse handleInvalidToken(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginNeededException.class)
    public ResultResponse handleLoginNeeded(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    public ResultResponse handlePasswordWrong(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationEntryPointException.class)
    public ResultResponse handleAuthenticationEntryPoint(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResultResponse handleAccessDenied(Exception e) {
        return ResultResponse.builder()
                .success("false")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
    }

}
