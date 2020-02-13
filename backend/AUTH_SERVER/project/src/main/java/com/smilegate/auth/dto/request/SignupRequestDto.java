package com.smilegate.auth.dto.request;

import lombok.*;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
}
