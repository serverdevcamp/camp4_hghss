package com.smilegate.auth.exceptions;

public class ExistEmailException extends RuntimeException {
    public ExistEmailException(String email) {
        super("이미 사용중인 이메일입니다. : " + email);
    }
}
