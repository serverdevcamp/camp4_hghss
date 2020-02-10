package com.smilegate.resume.controller;

import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.exceptions.InvalidTokenException;
import com.smilegate.resume.exceptions.TokenNotExistException;
import com.smilegate.resume.exceptions.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;

@ControllerAdvice
public class ResumeExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    public ResultResponse handleInvalidToken(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(402)
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

    @ResponseBody
    @ExceptionHandler(TokenNotExistException.class)
    public ResultResponse handleTokenNotExist(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(400)
                .message(e.getMessage())
                .data(new LinkedList<>())
                .build();
    }

}
