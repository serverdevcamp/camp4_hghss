package com.rest.recruit.exception;

import org.springframework.http.ResponseEntity;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("401 Unauthorized");
    }

}
