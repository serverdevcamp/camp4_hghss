package com.smilegate.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String email;

    private String hashedPassword;

    private Integer id;

    private String nickname;

    private String grade;

}
