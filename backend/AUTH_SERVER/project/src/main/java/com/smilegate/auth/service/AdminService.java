package com.smilegate.auth.service;

import com.smilegate.auth.domain.User;
import com.smilegate.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findUsers();
    }

    public boolean updateUserRole(int id, String role) {
        userRepository.updateRole(id, role);
        return true;
    }
}
