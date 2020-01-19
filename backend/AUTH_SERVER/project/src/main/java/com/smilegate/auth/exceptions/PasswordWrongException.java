package com.smilegate.auth.exceptions;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
