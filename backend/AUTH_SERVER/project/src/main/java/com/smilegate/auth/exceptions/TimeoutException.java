package com.smilegate.auth.exceptions;

public class TimeoutException extends RuntimeException {

    public TimeoutException() {
        super("인증 시간이 만료되었습니다.");
    }
}
