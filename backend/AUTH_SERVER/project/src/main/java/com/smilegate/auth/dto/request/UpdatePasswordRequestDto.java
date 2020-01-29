package com.smilegate.auth.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequestDto {
    private String key;
    private String password;
}
