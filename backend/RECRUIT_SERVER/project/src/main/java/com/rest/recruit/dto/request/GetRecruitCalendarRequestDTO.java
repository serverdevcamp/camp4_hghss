package com.rest.recruit.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetRecruitCalendarRequestDTO {

    private String startTime;
    private String endTime;
    private int userIdx = 0;

}
