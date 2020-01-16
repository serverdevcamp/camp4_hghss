package com.rest.user.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetRecruitCalendarSimpleResponseDTO {
    private int companyId;
    private int recruitId;
    private String companyName;
    private int recruitType;
    private String imageFileName;//EMPLOYEE TYPE, USER_LIKE
    private int employType;
    private String startTime;
    private String endTime;
}
