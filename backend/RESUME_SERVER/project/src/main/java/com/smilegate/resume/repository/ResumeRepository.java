package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.ResumeInfo;

import java.util.List;

public interface ResumeRepository {

    int createResume(ResumeInfo resumeInfo);

    int createAnswer(Answer answer);

    List<ResumeInfo> getList(int userId, String start, String end);
}
