package com.rest.recruit.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("401 Unauthorized");
    }
}
