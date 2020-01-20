package com.smilegate.auth.controller;

import com.smilegate.auth.dto.SigninRequestDto;
import com.smilegate.auth.dto.SigninResponseDto;
import com.smilegate.auth.dto.SignupRequestDto;
import com.smilegate.auth.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<SigninResponseDto> signin(@RequestBody SigninRequestDto signinRequestDto) {
        SigninResponseDto signinResponseDto = userService.signin(signinRequestDto);
        return ResponseEntity.ok().body(signinResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.sendSignupMail(signupRequestDto);
        return ResponseEntity.ok().body("인증 메일 전송");
    }

    @GetMapping("/signup/confirm")
    public ResponseEntity<?> signupConfirm(@RequestParam("key")String key) {
        userService.registerUser(key);
        return ResponseEntity.ok().body("가입 완료");
    }

    @PostMapping("/findPassword")
    public ResponseEntity<?> findPassword(@RequestBody Map<String, String> map) {
        userService.sendPasswordMail(map.get("email"));
        return ResponseEntity.ok().body("비밀번호 변경 메일 전송");
    }

    @GetMapping("/findPassword/confirm")
    public ResponseEntity<?> findPasswordConfirm(@RequestParam("key")String key) {
        String token = userService.getUpdatePasswordToken(key);
        return ResponseEntity.ok().body(token);
//        return ResponseEntity.ok().body(new FindPasswordConfirmResponseDto(token));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> update(@RequestBody Map<String, String> map, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        String password = map.get("password");

        userService.updatePassword(email, password);

        return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
    }

}
