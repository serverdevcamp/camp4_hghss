package com.smilegate.resume.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Answer {

    private Integer id;
    private Integer resumeId;
    private Integer orderNum;
    private String questionContent;
    private String answerContent;

}
