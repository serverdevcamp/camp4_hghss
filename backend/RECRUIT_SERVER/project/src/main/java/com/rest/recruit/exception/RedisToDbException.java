package com.rest.recruit.exception;

public class RedisToDbException extends RuntimeException {
    public RedisToDbException() { super("redis to db 에러"); }
}

