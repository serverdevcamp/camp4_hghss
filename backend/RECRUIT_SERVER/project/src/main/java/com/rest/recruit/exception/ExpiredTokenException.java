package com.rest.recruit.exception;

import org.springframework.http.ResponseEntity;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() { super("만료된 토큰입니다.");
    }
}
