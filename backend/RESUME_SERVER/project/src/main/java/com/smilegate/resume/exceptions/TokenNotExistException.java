package com.smilegate.resume.exceptions;

public class TokenNotExistException extends RuntimeException {

    public TokenNotExistException() {
        super("로그인하지 않은 사용자입니다.");
    }
}
