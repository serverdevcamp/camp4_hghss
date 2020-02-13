package com.smilegate.auth.exceptions;

public class SignoutException extends RuntimeException {
    public SignoutException() {
        super("로그아웃 상태입니다.");
    }
}
