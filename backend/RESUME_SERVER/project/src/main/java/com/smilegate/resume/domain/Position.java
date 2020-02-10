package com.smilegate.resume.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Position {
    private Integer id;
    private Integer recruitId;
    private String title;
    private Integer division;
}
