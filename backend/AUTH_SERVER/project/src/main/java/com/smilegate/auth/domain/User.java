package com.smilegate.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private String nickname;

    @Id
    private String email;

    private String passwd;

    private Integer role;

    private String name;

    private String createdAt;

    private String updatedAt;

    private String accessedAt;

    private Integer status;
}
