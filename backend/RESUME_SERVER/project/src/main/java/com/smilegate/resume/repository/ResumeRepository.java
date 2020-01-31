package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Position;
import com.smilegate.resume.domain.Resume;

import java.util.List;

public interface ResumeRepository {

    int createResume(Resume resume);

    int createAnswer(Answer answer);

    int updateAnswer(Answer answer);

    int updateTitle(int id, String title);

    List<Resume> findResumesByUserId(int userId);

    Position findPositionById(Integer positionId);

    String findEndDateById(Integer recruitId);
}
