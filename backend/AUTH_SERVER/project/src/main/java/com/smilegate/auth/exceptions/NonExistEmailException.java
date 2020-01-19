package com.smilegate.auth.exceptions;

public class NonExistEmailException extends RuntimeException {
    public NonExistEmailException(String email) {
        super("존재하지 않는 이메일입니다. : " + email);
    }
}
