package com.smilegate.resume.dto.request;

import lombok.*;

@Data
public class AnswerRequestDto {
    private String id;
    private String questionContent;
    private String answerContent;
}
