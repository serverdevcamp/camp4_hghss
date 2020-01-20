package com.smilegate.auth.exceptions;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
        super("만료된 Refresh Token입니다.");
    }
}
