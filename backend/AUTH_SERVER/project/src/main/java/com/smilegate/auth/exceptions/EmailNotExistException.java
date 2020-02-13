package com.smilegate.auth.exceptions;

public class EmailNotExistException extends RuntimeException {
    public EmailNotExistException(String email) {
        super("존재하지 않는 이메일 입니다. : " + email);
    }
}
