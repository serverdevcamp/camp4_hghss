package com.rest.user.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCalendarResponse {
    private int companyId;
    private int recruitId;
    private String companyName;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String imageFileName;//EMPLOYEE TYPE, USER_LIKE
    private List<Integer> employType;


    public GetCalendarResponse(int recruitId, int companyId, String companyName, String imageFileName,
                               int recruitType,String startTime,String endTime,List<Integer> employTypeTmp) {

        this.companyId = companyId;
        this.recruitId = recruitId;
        this.companyName = companyName;
        this.recruitType = recruitType;
        this.imageFileName = imageFileName;
        this.employType = employTypeTmp;
        this.startTime = startTime;
        this.endTime = endTime;

    }
}
