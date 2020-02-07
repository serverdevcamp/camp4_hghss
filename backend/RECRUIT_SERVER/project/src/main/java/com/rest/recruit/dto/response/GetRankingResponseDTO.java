package com.rest.recruit.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRankingResponseDTO {
    private int rank;
    private int recruitId;
    private int companyId;
    private double count;
    private String companyName;
    private String endTime;

    public GetRankingResponseDTO(String[] array, double count, int rank) {

        this.recruitId = Integer.parseInt(array[1]);
        this.companyId = Integer.parseInt(array[2]);
        this.companyName = array[3];
        this.count = count;
        this.rank  = rank;
        this.endTime = array[0];
    }
}
