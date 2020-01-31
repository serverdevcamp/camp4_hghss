package com.smilegate.resume.controller;

import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.exceptions.InvalidTokenException;
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
                .status(400)
                .message(e.getMessage())
                .build();
    }

}
