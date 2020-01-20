package com.rest.recruit.dto.response;

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
    private String employType;
    private String startTime;
    private String endTime;


}
