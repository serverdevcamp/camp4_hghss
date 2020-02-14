package com.smilegate.resume.exceptions;

public class AnswerNotExistException extends RuntimeException {

    public AnswerNotExistException(int answerId) {
        super("해당 문항이 존재하지 않습니다. answer_id : " + answerId);
    }

}
