package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Position;
import com.smilegate.resume.domain.Resume;

import java.util.List;

public interface ResumeRepository {

    int createResume(Resume resume);

    int createAnswer(Answer answer);

    int updateAnswer(Answer answer);

    int updateTitle(int id, String title, String date);

    List<Resume> findResumesByUserId(int userId);

    Resume findResumeById(int resumeId);

    int deleteAnswers(int resumeId);

    int deleteResume(int resumeId);

    int updateResumePosition(int resumeId, int col, int row);

    List<Answer> findAnswersByResumeId(int resumeId);

    int countAnswer(int resumeId);

    int findResumeIdByAnswerId(int answerId);

    int deleteAnswer(int answerId);
}
