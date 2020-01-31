package com.smilegate.resume.service;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Position;
import com.smilegate.resume.domain.Resume;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public int create(int userId, int positionId, ResumeRequestDto resume) {

        Resume resumeInfo = Resume.builder()
                                        .userId(userId)
                                        .positionId(positionId)
                                        .title(resume.getTitle())
                                        .build();

        int resumeId = resumeRepository.createResume(resumeInfo);

        List<Answer> answers = resume.getAnswers();

        for(int i=0; i<answers.size(); ++i) {
            Answer answerRequestDto = answers.get(i);

            Answer answer = Answer.builder()
                                .resumeId(resumeId)
                                .orderNum(i+1)
                                .questionContent(answerRequestDto.getQuestionContent())
                                .answerContent(answerRequestDto.getAnswerContent())
                                .build();

            resumeRepository.createAnswer(answer);
        }

        return resumeId;
    }

    public List<Resume> getResumes(int userId) {

        List<Resume> resumes = resumeRepository.findResumesByUserId(userId);

        for(Resume resume : resumes) {
            Position position = resumeRepository.findPositionById(resume.getPositionId());
            String endDate = resumeRepository.findEndDateById(position.getRecruitId());
            resume.setEndDate(endDate);
        }

        return resumes;
    }

    public boolean saveResume(int id, String title, List<Answer> answers) {

        // TODO: resume가 존재하는지

        // TODO: resume 저장
        resumeRepository.updateTitle(id, title);

        for(int i=0; i<answers.size(); ++i) {
            Answer answer = answers.get(i);
            answer.setOrderNum(i+1);
            resumeRepository.updateAnswer(answer);
        }

        return true;
    }
}
