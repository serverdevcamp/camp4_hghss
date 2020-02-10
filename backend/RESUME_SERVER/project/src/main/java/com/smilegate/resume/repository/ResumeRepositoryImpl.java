package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResumeRepositoryImpl implements ResumeRepository {

    private final SqlSession session;

    @Override
    public int createResume(Resume resume) {
        session.insert("resume.insertResume", resume);
        return resume.getId();
    }

    @Override
    public int createAnswer(Answer answer) {
        session.insert("resume.insertAnswer", answer);
        return answer.getId();
    }

    @Override
    public int updateAnswer(Answer answer) {
        return session.update("resume.updateAnswer", answer);
    }

    @Override
    public int updateTitle(int id, String title, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("title", title);
        map.put("date", date);
        return session.update("resume.updateTitle", map);
    }

    @Override
    public List<Resume> findResumesByUserId(int userId) {
        return session.selectList("resume.selectResumeByUserId", userId);
    }

    @Override
    public Resume findResumeById(int resumeId) {
        return session.selectOne("resume.selectResumeById", resumeId);
    }

    @Override
    public int deleteAnswers(int resumeId) {
        return session.delete("resume.deleteAnswers", resumeId);
    }

    @Override
    public int deleteResume(int resumeId) {
        return session.delete("resume.deleteResume", resumeId);
    }

    @Override
    public int updateResumePosition(int resumeId, int col, int row) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("resumeId", resumeId);
        map.put("col", col);
        map.put("row", row);
        return session.update("resume.updateResumePosition", map);
    }

    @Override
    public List<Answer> findAnswersByResumeId(int resumeId) {
        return session.selectList("resume.selectAnswerByResumeId", resumeId);
    }

    @Override
    public int countAnswer(int resumeId) {
        return session.selectOne("resume.selectCntAnswerByResumeId", resumeId);
    }

    @Override
    public int findResumeIdByAnswerId(int answerId) {
        return session.selectOne("resume.selectResumeIdByAnswerId", answerId);
    }

    @Override
    public int deleteAnswer(int answerId) {
        return session.delete("resume.deleteAnswer", answerId);
    }

}