package com.smilegate.resume.service;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Resume;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.dto.response.ResumeDetailResponseDto;
import com.smilegate.resume.exceptions.AnswerNotExistException;
import com.smilegate.resume.exceptions.ResumeNotExistException;
import com.smilegate.resume.exceptions.UnauthorizedException;
import com.smilegate.resume.repository.ResumeRepository;
import com.smilegate.resume.utils.DateUtil;
import com.smilegate.resume.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final JwtUtil jwtUtil;
    private final DateUtil dateUtil;

    @Transactional
    public ResumeDetailResponseDto createResume(String token, int positionId, ResumeRequestDto resumeRequestDto) {

        int userId = getUserId(token);
        int recruitId = resumeRepository.findRecruitIdByPositionId(positionId);
        int companyId = resumeRepository.findCompanyIdByRecruitId(recruitId);

        Resume resume = Resume.builder()
                            .userId(userId)
                            .recruitId(recruitId)
                            .companyId(companyId)
                            .positionId(positionId)
                            .title(resumeRequestDto.getTitle())
                            .endTime(resumeRequestDto.getEndTime())
                            .build();

        int resumeId = resumeRepository.createResume(resume);
        resume = resumeRepository.findResumeById(resumeId);

        List<Answer> answers = resumeRequestDto.getAnswers();

        for(int i=0; i<answers.size(); ++i) {
            Answer answer = answers.get(i);
            answer.setResumeId(resumeId);
            answer.setOrderNum(i+1);

            resumeRepository.createAnswer(answer);
        }

        return ResumeDetailResponseDto.builder()
                .resume(resume)
                .answers(answers)
                .build();
    }

    public List<Resume> getResumes(String token) {
        int userId = getUserId(token);
        List<Resume> resumes = resumeRepository.findResumesByUserId(userId);
        return resumes;
    }

    @Transactional
    public boolean saveResume(int resumeId, String token, String title, List<Answer> answers) {

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.updateTitle(resumeId, title, dateUtil.now());

        for(int i=0; i<answers.size(); ++i) {
            Answer answer = answers.get(i);
            answer.setResumeId(resumeId);
            int updateCnt = resumeRepository.updateAnswer(answer);
            if(updateCnt < 1) throw new AnswerNotExistException(answer.getId());
        }

        return true;
    }

    @Transactional
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

        int order = resumeRepository.findMaxOrderNumByResumeId(resumeId);

        Answer answer = Answer.builder()
                            .resumeId(resumeId)
                            .orderNum(order+1)
                            .questionLimit(1000)
                            .build();

        resumeRepository.createAnswer(answer);

        return answer;
    }

    public boolean deleteAnswer(String token, int answerId) {

        Integer resumeId = resumeRepository.findResumeIdByAnswerId(answerId);
        if(resumeId == null) throw new AnswerNotExistException(answerId);

        if(!checkAuth(token, resumeId)) return false;

        resumeRepository.deleteAnswer(answerId);

        return true;
    }

    public int countResume(String token, int positionId) {
        int userId = getUserId(token);
        return resumeRepository.countResumeByPositionId(userId, positionId);
    }

    private boolean checkAuth(String token, int resumeId) {

        Resume resume = resumeRepository.findResumeById(resumeId);
        if(resume == null) throw new ResumeNotExistException(resumeId);

        int userId = getUserId(token);
        if(userId != resume.getUserId()) throw new UnauthorizedException();

        return true;
    }

    private int getUserId(String token) {
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        return (int) claims.get("userId");
    }
}
