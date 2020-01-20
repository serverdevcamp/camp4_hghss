package com.smilegate.auth.exceptions;

public class BlackListTokenException extends RuntimeException {
    public BlackListTokenException() {
        super("로그아웃 상태입니다.");
    }
}
