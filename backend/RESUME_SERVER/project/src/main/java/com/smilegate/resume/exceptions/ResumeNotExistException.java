package com.smilegate.resume.exceptions;

public class ResumeNotExistException extends RuntimeException {

    public ResumeNotExistException(int resumeId) {
        super("자기소개서가 존재하지 않습니다. resume_id : " + resumeId);
    }

}
