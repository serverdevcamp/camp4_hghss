package com.rest.recruit.exception;

public class UnValidatedDateTypeException extends RuntimeException{
    public UnValidatedDateTypeException() {
        super("유효하지않은 date type 입니다.");
    }
}
