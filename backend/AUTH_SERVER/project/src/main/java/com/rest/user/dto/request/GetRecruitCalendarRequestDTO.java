package com.rest.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRecruitCalendarRequestDTO {

    private String startTime;
    private String endTime;
}
