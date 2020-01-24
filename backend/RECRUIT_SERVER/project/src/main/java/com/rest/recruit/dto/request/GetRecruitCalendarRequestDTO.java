package com.rest.recruit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecruitCalendarRequestDTO {

    private String startTime;
    private String endTime;

    public GetRecruitCalendarRequestDTO(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
