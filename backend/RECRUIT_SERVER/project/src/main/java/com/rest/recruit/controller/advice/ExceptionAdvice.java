package com.rest.recruit.controller.advice;


import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.exception.GetCalendarException;
import com.rest.recruit.exception.GetDetailRecruitPageException;
import com.rest.recruit.exception.RedisToDbException;
import com.rest.recruit.exception.UnValidatedDateTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("지원하지 않는 HTTP method 호출입니다")
                .status("400")
                .success("false").build());
    }


    /**
     *  handleException
     *  그 밖에 발생하는 모든 예외 처리, Null Point Exception, 등등
     *  개발자가 직접 핸들링해서 다른 예외로 던지지 않으면 모두 이곳으로 모인다.
     */

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .status("500")
                .success("false").build());
    }
    /**
     *  MissingServletRequestParameterException
     *  parameter가 없을 때
     *
     */

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingParams(MissingServletRequestParameterException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("필요한 값이 잘못되었습니다.")
                .status("400")
                .success("false").build());
    }

    @ExceptionHandler(UnValidatedDateTypeException.class)
    public ResponseEntity handleDateType(UnValidatedDateTypeException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message(e.getMessage()) //유효하지 않은 date type입니다.
                .status("400")
                .success("false").build());
    }

    @ExceptionHandler(GetCalendarException.class)
    public ResponseEntity handleGetCalendar(GetCalendarException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message(e.getMessage())
                .status("500")
                .success("false").build());
    }

    @ExceptionHandler(GetDetailRecruitPageException.class)
    public ResponseEntity handleGetDetailRecruitPage(GetDetailRecruitPageException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message(e.getMessage())
                .status("500")
                .success("false").build());

    }

    @ExceptionHandler(RedisToDbException.class)
    public ResponseEntity handleRedisToDB(RedisToDbException e) {
        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message(e.getMessage())
                .status("500")
                .success("false").build());
    }
}
