package com.smilegate.resume.controller;

import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.exceptions.InvalidTokenException;
import com.smilegate.resume.exceptions.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResumeExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    public ResultResponse handleInvalidToken(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(401)
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultResponse handleUnauthorized(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(403)
                .message(e.getMessage())
                .build();
    }

}
