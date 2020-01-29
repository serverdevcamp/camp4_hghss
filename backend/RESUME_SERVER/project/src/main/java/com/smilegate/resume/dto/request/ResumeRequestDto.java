package com.smilegate.resume.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ResumeRequestDto {
    private String title;
    private List<AnswerRequestDto> answers;
}
