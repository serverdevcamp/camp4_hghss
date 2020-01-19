package com.smilegate.auth.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {
    UserDetails findByEmail(String email);
}
