package com.smilegate.auth.exceptions;

public class LoginNeededException extends RuntimeException {
    public LoginNeededException() {
        super("로그인이 필요한 서비스입니다.");
    }
}
