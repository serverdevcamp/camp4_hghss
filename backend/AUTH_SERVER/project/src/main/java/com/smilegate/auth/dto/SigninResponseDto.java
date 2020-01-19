package com.smilegate.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SigninResponseDto {

    private String accessToken;
    private String refreshToken;

}
