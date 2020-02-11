package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Calendars {
    private int recruitId;
    private int companyId;
    private String companyName;
    private int recruitType;
    private String imageFileName;//EMPLOYEE TYPE, USER_LIKE
    private String employType;
    private String startTime;
    private String endTime;
}
