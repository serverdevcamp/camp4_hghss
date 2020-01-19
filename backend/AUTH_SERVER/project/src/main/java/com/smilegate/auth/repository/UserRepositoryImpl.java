package com.smilegate.auth.repository;

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
    public UserDetails findByEmail(String email) {
        return session.selectOne("user.findByEmail", email);
    }

}
