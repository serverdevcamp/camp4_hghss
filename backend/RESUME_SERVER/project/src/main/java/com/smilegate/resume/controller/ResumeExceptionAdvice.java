package com.smilegate.resume.controller;

import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.exceptions.*;
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
                .status(200)
                .message(e.getMessage())
                .data(new LinkedList<>())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ResumeNotExistException.class)
    public ResultResponse handleResumeNotExist(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(500)
                .message(e.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(AnswerNotExistException.class)
    public ResultResponse handleAnswerNotExist(Exception e) {
        return ResultResponse.builder()
                .success(false)
                .status(501)
                .message(e.getMessage())
                .build();
    }

}
