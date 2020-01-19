package com.smilegate.auth.service;

import com.smilegate.auth.config.security.JwtTokenProvider;
import com.smilegate.auth.domain.User;
import com.smilegate.auth.dto.SigninRequestDto;
import com.smilegate.auth.dto.SigninResponseDto;
import com.smilegate.auth.exceptions.EmailNotExistException;
import com.smilegate.auth.exceptions.PasswordWrongException;
import com.smilegate.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SigninResponseDto signin(SigninRequestDto signinRequestDto) {
        String email = signinRequestDto.getEmail();
        String password = signinRequestDto.getPassword();

        // email 체크
        User user = (User) userRepository.findByEmail(email);
        if(user==null) throw new EmailNotExistException(email);

        // password 체크
        if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new PasswordWrongException();
        }

        // token 발급
        String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getGrade(), 30);
        String refreshToken = jwtTokenProvider.createToken(user.getEmail(), user.getGrade(), 60*24*14);

        return new SigninResponseDto(accessToken, refreshToken);
    }
}
