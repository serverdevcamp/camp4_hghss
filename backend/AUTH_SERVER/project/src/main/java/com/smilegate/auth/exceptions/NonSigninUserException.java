package com.smilegate.auth.exceptions;

public class NonSigninUserException extends RuntimeException {
    public NonSigninUserException() {
        super("로그인하지 않은 사용자입니다.");
    }
}
