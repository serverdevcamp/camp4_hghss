package com.rest.recruit.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SimpleResponse {

    public static ResponseEntity ok() { return SimpleResponse.msg(HttpStatus.OK); }

    public static ResponseEntity ok(Object o) {
        return SimpleResponse.msg(HttpStatus.OK, o);
    }

    public static ResponseEntity badRequest() {
        return SimpleResponse.msg(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity notExceptable(){return SimpleResponse.msg(HttpStatus.NOT_ACCEPTABLE); }

    public static ResponseEntity badRequest(Object o) {
        return SimpleResponse.msg(HttpStatus.BAD_REQUEST, o);
    }

    public static ResponseEntity internalServerError() {
        return SimpleResponse.msg(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity msg(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }

    public static ResponseEntity internalServerError(Object o) {
        return SimpleResponse.msg(HttpStatus.INTERNAL_SERVER_ERROR, o);
    }

    public static ResponseEntity msg(HttpStatus httpStatus, Object o) {
        return ResponseEntity.status(httpStatus).body(o);
    }



}

