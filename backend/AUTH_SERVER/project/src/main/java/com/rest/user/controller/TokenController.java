package com.rest.user.controller;


import com.rest.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    //refresh token
    @GetMapping("/refresh")
    public ResponseEntity refresh(@RequestParam("refreshToken") String refreshToken){
        return tokenService.refresh(refreshToken);

    }
}