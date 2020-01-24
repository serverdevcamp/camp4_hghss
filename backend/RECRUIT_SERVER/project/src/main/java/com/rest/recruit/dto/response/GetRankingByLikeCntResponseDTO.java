package com.rest.recruit.dto.response;

public class GetRankingByLikeCntResponseDTO {
    private int rank;
    private int recruitId;
    private int companyId;
    private double favoriteCount;
    private String companyName;


    //String tmpString = tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
    //                    tmp.getCompanyId() + ":" + tmp.getCompanyName();

    public GetRankingByLikeCntResponseDTO(String[] array,double favoriteCount,int rank) {

        this.recruitId = Integer.parseInt(array[1]);
        this.companyId = Integer.parseInt(array[2]);
        this.companyName = array[3];
        this.favoriteCount = favoriteCount;
        this.rank  = rank;
    }
}
