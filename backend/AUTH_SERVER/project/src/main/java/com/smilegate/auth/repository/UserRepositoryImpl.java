package com.smilegate.auth.repository;

import com.smilegate.auth.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SqlSession session;

    public UserRepositoryImpl(SqlSession session) {
        this.session = session;
    }

    @Override
    public UserDetails findUserDetailsByEmail(String email) {
        return session.selectOne("user.findUserDetailsByEmail", email);
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
        return session.insert("user.signup", user);
    }

    @Override
    public void updatePassword(User user) {
        session.update("user.updatePassword", user);
    }

    @Override
    public List<User> findUsers() {
        return session.selectList("user.findUsers");
    }

}
