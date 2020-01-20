package com.smilegate.auth.repository;

import com.smilegate.auth.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository {
    UserDetails findUserDetailsByEmail(String email);

    User findByEmail(String email);

    int countUser(String email);

    int registerUser(User user);

    void updatePassword(User build);

    List<User> findUsers();
}
