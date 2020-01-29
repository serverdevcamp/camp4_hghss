package com.smilegate.auth.repository;

import com.smilegate.auth.domain.User;

import java.util.List;

public interface UserRepository {

    User findByEmail(String email);

    int countUser(String email);

    int registerUser(User user);

    void updatePassword(User build);

    List<User> findUsers();

    String getNickname(int userId);

    void updateNickname(int userId, String nickname);

    int updateRole(int id, String role);

}
