package com.smilegate.auth.repository;

import com.smilegate.auth.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SqlSession session;

    public UserRepositoryImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public User findByEmail(String email) {
        return session.selectOne("user.findByEmail", email);
    }

    @Override
    public int countUser(String email) {
        return session.selectOne("user.countUser", email);
    }

    @Override
    public int registerUser(User user) {
        session.insert("user.signup", user);
        return user.getId();
    }

    @Override
    public void updatePassword(User user) {
        session.update("user.updatePassword", user);
    }

    @Override
    public List<User> findUsers() {
        return session.selectList("user.findUsers");
    }

    @Override
    public String getNickname(int id) {
        return session.selectOne("user.getNickname", id);
    }

    @Override
    public void updateNickname(int userId, String nickname) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(userId));
        map.put("nickname", nickname);
        session.update("user.updateNickname", map);
    }

    @Override
    public int updateRole(int id, String role) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(id));
        map.put("role", role);
        return session.update("user.updateRole",map);
    }

}
