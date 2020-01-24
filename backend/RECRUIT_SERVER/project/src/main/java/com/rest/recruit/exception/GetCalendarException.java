package com.rest.recruit.exception;

public class GetCalendarException extends RuntimeException{
    public GetCalendarException() {
        super("캘린더 조회 에러입니다.");
    }
}
