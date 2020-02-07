package com.smilegate.resume.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Answer {

    private Integer id;
    private Integer resumeId;
    private Integer orderNum;
    private String questionContent;
    private String answerContent;
    private Integer questionLimit;

}
