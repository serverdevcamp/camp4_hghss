package com.smilegate.resume.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Resume {

    private Integer id;
    private Integer userId;
    private Integer recruitId;
    private Integer companyId;
    private Integer positionId;
    private String createdAt;
    private String updatedAt;
    private String title;
    private Integer resumeCol;
    private Integer resumeRow;
    private String endTime;

}
