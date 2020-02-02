package com.smilegate.resume.service;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Resume;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.dto.response.ResumeDetailResponseDto;
import com.smilegate.resume.exceptions.UnauthorizedException;
import com.smilegate.resume.repository.ResumeRepository;
import com.smilegate.resume.utils.DateUtil;
import com.smilegate.resume.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final JwtUtil jwtUtil;
    private final DateUtil dateUtil;

    public int createResume(String token, int positionId, ResumeRequestDto resumeRequestDto) {

        int userId = getUserId(token);
        Resume resume = Resume.builder()
                            .userId(userId)
                            .positionId(positionId)
                            .title(resumeRequestDto.getTitle())
                            .endTime(resumeRequestDto.getEndTime())
                            .build();

        int resumeId = resumeRepository.createResume(resume);

        List<Answer> answers = resumeRequestDto.getAnswers();

        for(int i=0; i<answers.size(); ++i) {
            Answer answer = answers.get(i);
            answer.setResumeId(resumeId);
            answer.setOrderNum(i+1);
            resumeRepository.createAnswer(answer);
        }

        return resumeId;
    }

    public List<Resume> getResumes(String token) {

        int userId = getUserId(token);
        List<Resume> resumes = resumeRepository.findResumesByUserId(userId);

        return resumes;
    }

    public boolean saveResume(int resumeId, String token, String title, List<Answer> answers) {

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.updateTitle(resumeId, title, dateUtil.now());

        for(int i=0; i<answers.size(); ++i) {
            Answer answer = answers.get(i);
            answer.setOrderNum(i+1);
            resumeRepository.updateAnswer(answer);
        }

        return true;
    }

    public boolean deleteResume(int resumeId, String token) {

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.deleteAnswers(resumeId);
        resumeRepository.deleteResume(resumeId);

        return true;
    }

    public boolean moveResume(int resumeId, String token, int col, int row) {

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.updateResumePosition(resumeId, col, row);

        return true;
    }

    public ResumeDetailResponseDto getResume(String token, int resumeId) {

        if(!checkAuth(token, resumeId)) return null;

        Resume resume = resumeRepository.findResumeById(resumeId);
        List<Answer> answers = resumeRepository.findAnswersByResumeId(resumeId);

        return ResumeDetailResponseDto.builder()
                .resume(resume)
                .answers(answers)
                .build();
    }

    public Answer createAnswer(String token, int resumeId) {

        if(!checkAuth(token, resumeId)) return null;

        int cnt = resumeRepository.countAnswer(resumeId);

        Answer answer = Answer.builder()
                            .resumeId(resumeId)
                            .orderNum(cnt+1)
                            .questionLimit(1000)
                            .build();

        resumeRepository.createAnswer(answer);

        return answer;
    }

    public boolean deleteAnswer(String token, int answerId) {

        int resumeId = resumeRepository.findResumeIdByAnswerId(answerId);

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.deleteAnswer(answerId);

        return true;
    }

    private boolean checkAuth(String token, int resumeId) {

        Resume resume = resumeRepository.findResumeById(resumeId);
        int userId = getUserId(token);
        if(userId != resume.getUserId()) throw new UnauthorizedException();

        return true;
    }

    private int getUserId(String token) {
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        return (int) claims.get("userId");
    }
}
