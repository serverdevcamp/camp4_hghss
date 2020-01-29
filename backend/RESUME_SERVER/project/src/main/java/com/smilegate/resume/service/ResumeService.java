package com.smilegate.resume.service;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.ResumeInfo;
import com.smilegate.resume.dto.request.AnswerRequestDto;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public int create(int userId, int positionId, ResumeRequestDto resume) {

        ResumeInfo resumeInfo = ResumeInfo.builder()
                                        .userId(userId)
                                        .positionId(positionId)
                                        .lastModDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                                        .title(resume.getTitle())
                                        .index(11)
                                        .build();

        int resumeId = resumeRepository.createResume(resumeInfo);

        List<AnswerRequestDto> answers = resume.getAnswers();

        for(int i=0; i<answers.size(); ++i) {
            AnswerRequestDto answerRequestDto = answers.get(i);

            Answer answer = Answer.builder()
                                .resumeId(resumeId)
                                .order(i+1)
                                .questionContent(answerRequestDto.getQuestionContent())
                                .answerContent(answerRequestDto.getAnswerContent())
                                .build();

            resumeRepository.createAnswer(answer);
        }

        return resumeId;
    }

    public List<ResumeInfo> getList(int userId, String start, String end) {
        return resumeRepository.getList(userId, start, end);
    }
}
