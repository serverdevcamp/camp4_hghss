package com.smilegate.resume.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResumeInfo {

    private Integer id;
    private Integer userId;
    private Integer positionId;
    private String lastModDate;
    private String title;
    private Integer index;

}
