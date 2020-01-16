package com.rest.user.dto.response;

import com.rest.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertUserResponseDTO {
    private String hashedPassword;
    private String email;
    private String salt;

    public static InsertUserResponseDTO of(User user){
        return InsertUserResponseDTO.builder()
                .hashedPassword(user.getHashedPassword())
                .email(user.getEmail())
                .salt(user.getSalt())
                .build();
    }
}
