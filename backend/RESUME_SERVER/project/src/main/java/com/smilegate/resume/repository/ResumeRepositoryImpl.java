package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Position;
import com.smilegate.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public int updateTitle(int id, String title) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("title", title);
        map.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        return session.update("resume.updateTitle", map);
    }

    @Override
    public List<Resume> findResumesByUserId(int userId) {
        return session.selectList("resume.selectResume", userId);
    }

    @Override
    public Position findPositionById(Integer positionId) {
        return session.selectOne("resume.selectPosition", positionId);
    }

    @Override
    public String findEndDateById(Integer recruitId) {
        return session.selectOne("resume.selectEndDate", recruitId);
    }

}
