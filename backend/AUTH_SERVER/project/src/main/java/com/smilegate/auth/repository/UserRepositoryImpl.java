package com.smilegate.auth.repository;

import com.smilegate.auth.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

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

}
