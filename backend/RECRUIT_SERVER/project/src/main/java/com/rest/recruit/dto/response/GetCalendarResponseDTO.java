package com.rest.recruit.dto.response;

import com.rest.recruit.model.Calendars;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetCalendarResponseDTO {
    private int companyId;
    private int recruitId;
    private String companyName;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String imageFileName;//EMPLOYEE TYPE, USER_LIKE
    private List<Integer> employType;


    private static List<Integer> convertToArray(String employType) {
        List<String> tmpString = Arrays.asList(employType.split(","));

        return tmpString.stream()
                .map(s-> Integer.valueOf(s))
                .collect(Collectors.toList());

    }

    public static GetCalendarResponseDTO of(Calendars calendars) {
        return GetCalendarResponseDTO.builder()
                .companyId(calendars.getCompanyId())
                .recruitId(calendars.getRecruitId())
                .companyName(calendars.getCompanyName())
                .recruitType(calendars.getRecruitType())
                .imageFileName(calendars.getImageFileName())
                .startTime(calendars.getStartTime())
                .endTime(calendars.getEndTime())
                .employType(convertToArray(calendars.getEmployType()))
                .build();
    }
}