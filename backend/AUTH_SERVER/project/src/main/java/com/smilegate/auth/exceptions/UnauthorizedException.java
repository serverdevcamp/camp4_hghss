package com.smilegate.auth.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("해당 리소스에 접근하기 위한 권한이 없습니다.");
    }
}
