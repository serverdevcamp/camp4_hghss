package com.rest.recruit.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRankingByVisitCntResponseDTO {
    private int rank;
    private int recruitId;
    private int companyId;
    private double viewCount;
    private String companyName;


    //String tmpString = tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
    //                    tmp.getCompanyId() + ":" + tmp.getCompanyName();

    public GetRankingByVisitCntResponseDTO(String[] array,double viewCount,int rank) {

        this.recruitId = Integer.parseInt(array[1]);
        this.companyId = Integer.parseInt(array[2]);
        this.companyName = array[3];
        this.viewCount = viewCount;
        this.rank  = rank;
    }
}
