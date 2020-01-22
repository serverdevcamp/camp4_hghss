package com.smilegate.auth.dto.response;

import lombok.*;

@Builder
@Data
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String email;
    private String nickname;

}
