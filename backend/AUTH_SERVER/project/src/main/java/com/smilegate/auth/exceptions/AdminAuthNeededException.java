package com.smilegate.auth.exceptions;

public class AdminAuthNeededException extends RuntimeException {
    public AdminAuthNeededException() {
        super("관리자 권한이 필요합니다.");
    }
}
