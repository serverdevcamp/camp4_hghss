package com.smilegate.resume.dto.response;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Resume;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResumeDetailResponseDto {

    private Resume resume;
    private List<Answer> answers;

}
