package com.smilegate.resume.repository;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.ResumeInfo;
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
    public int createResume(ResumeInfo resumeInfo) {
        session.insert("resume.insertResume", resumeInfo);
        return resumeInfo.getId();
    }

    @Override
    public int createAnswer(Answer answer) {
        session.insert("resume.insertAnswer", answer);
        return answer.getId();
    }

    @Override
    public List<ResumeInfo> getList(int userId, String start, String end) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(userId));
        map.put("start", start);
        map.put("end", end);
        return session.selectList("resume.selectResumes", map);
    }

}
