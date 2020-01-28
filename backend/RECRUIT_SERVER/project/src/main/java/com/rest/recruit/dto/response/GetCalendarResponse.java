package com.rest.recruit.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetCalendarResponse {
    private int companyId;
    private int recruitId;
    private String companyName;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String imageFileName;//EMPLOYEE TYPE, USER_LIKE

    private boolean favorite;

    private List<Integer> employType;



    public static GetCalendarResponse of(GetRecruitCalendarSimpleResponseDTO getRecruitCalendarSimpleResponseDTO) {
        //convert string to array

        return GetCalendarResponse.builder()
                .companyId(getRecruitCalendarSimpleResponseDTO.getCompanyId())
                .recruitId(getRecruitCalendarSimpleResponseDTO.getRecruitId())
                .companyName(getRecruitCalendarSimpleResponseDTO.getCompanyName())
                .recruitType(getRecruitCalendarSimpleResponseDTO.getRecruitType())
                .imageFileName(getRecruitCalendarSimpleResponseDTO.getImageFileName())
                .startTime(getRecruitCalendarSimpleResponseDTO.getStartTime())
                .endTime(getRecruitCalendarSimpleResponseDTO.getEndTime())
                .favorite(getRecruitCalendarSimpleResponseDTO.getFavorite() == 1 ? true : false)
                .employType(convertToArray(getRecruitCalendarSimpleResponseDTO.getEmployType()))
                .build();
    }

    private static List<Integer> convertToArray(String employType) {
        List<String> tmpString = Arrays.asList(employType.split(","));

        return tmpString.stream()
                .map(s-> Integer.valueOf(s))
                .collect(Collectors.toList());

    }

}
