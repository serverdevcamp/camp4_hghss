package com.smilegate.resume.dto.request;

import com.smilegate.resume.domain.Answer;
import lombok.Data;

import java.util.List;

@Data
public class ResumeRequestDto {
    private String title;
    private List<Answer> answers;
}
