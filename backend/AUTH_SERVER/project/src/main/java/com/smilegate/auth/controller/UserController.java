package com.smilegate.auth.controller;

import com.smilegate.auth.dto.SigninRequestDto;
import com.smilegate.auth.dto.SigninResponseDto;
import com.smilegate.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signin(@RequestBody SigninRequestDto signinRequestDto) {
        SigninResponseDto signinResponseDto = userService.signin(signinRequestDto);
        return ResponseEntity.ok().body(signinResponseDto);
    }

    @GetMapping("/signup")
    public String signup() {
        return "singup";
    }

    @GetMapping("/signup/confirm")
    public String signupConfirm() {
        return "singup confirm";
    }


}
