package com.smilegate.auth.controller;

import com.smilegate.auth.dto.ResultResponse;
import com.smilegate.auth.dto.request.*;
import com.smilegate.auth.dto.response.TokenResponseDto;
import com.smilegate.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello!!!";
    }

    @PostMapping("/signin")
    public ResponseEntity<ResultResponse> signin(@RequestBody SigninRequestDto signinRequestDto) {
        TokenResponseDto tokenResponseDto = userService.signin(signinRequestDto);
        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success("true")
                        .status(HttpStatus.OK.value())
                        .message("로그인 되었습니다.")
                        .data(tokenResponseDto)
                        .build()
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.sendSignupMail(signupRequestDto);
        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success("true")
                        .status(HttpStatus.OK.value())
                        .message("인증 메일을 보냈습니다.")
                        .build()
        );
    }

    @GetMapping("/signup/confirm")
    public void signupConfirm(@RequestParam("key")String key, HttpServletResponse response) throws IOException {
        userService.registerUser(key);
        response.sendRedirect("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @PostMapping("/findPassword")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) {
        userService.sendPasswordMail(findPasswordRequestDto.getEmail());
        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success("true")
                        .status(HttpStatus.OK.value())
                        .message("비밀번호 변경 메일을 보냈습니다.")
                        .build()
        );
    }

    @GetMapping("/findPassword/confirm")
    public void findPasswordConfirm(@RequestParam("key")String key, HttpServletResponse response) throws IOException {
        String token = userService.getUpdatePasswordToken(key);
        response.setHeader("token", token);
        response.sendRedirect("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> update(
            @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        String password = updatePasswordRequestDto.getPassword();

        userService.updatePassword(email, password);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success("true")
                        .status(HttpStatus.OK.value())
                        .message("비밀번호가 변경되었습니다.")
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResultResponse> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequestDto
    ) {
        TokenResponseDto tokenResponseDto = userService.refreshToken(refreshTokenRequestDto.getRefreshToken());

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success("true")
                        .status(HttpStatus.OK.value())
                        .message("새로운 Access Token이 발급되었습니다.")
                        .data(tokenResponseDto)
                        .build()
        );
    }

}
