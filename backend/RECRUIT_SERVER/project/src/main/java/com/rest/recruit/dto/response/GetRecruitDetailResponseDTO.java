package com.rest.recruit.dto.response;

import com.rest.recruit.model.Position;
import com.rest.recruit.model.RecruitDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class GetRecruitDetailResponseDTO {

    private int recruitId;
    private String companyName;
    private String imageFileName;
    private String employmentPageUrl;
    private String startTime;
    private String endTime;
    private boolean favorite;
    private int recruitType;
    private String content;
    private int viewCount;
    private int favoriteCount;
    private GetRecruitPositionResponseDTO employments;

 /*   private RecruitDetail tmp;
    private GetRecruitPositionResponseDTO getRecruitPositionResponseDTO;
*/


    //좋아요 여부!!


    public GetRecruitDetailResponseDTO(RecruitDetail tmp, GetRecruitPositionResponseDTO getRecruitPositionResponseDTO) {

        this.recruitId = tmp.getRecruitId();
        this.companyName = tmp.getCompanyName();
        this.imageFileName = tmp.getImageFileName();
        this.employmentPageUrl = tmp.getEmploymentPageUrl();
        this.startTime = tmp.getStartTime();
        this.endTime = tmp.getEndTime();
        this.recruitType = tmp.getRecruitType();
        this.content = tmp.getContent();
        this.viewCount = tmp.getViewCount();
        this.favoriteCount = tmp.getFavoriteCount();
        this.employments = getRecruitPositionResponseDTO;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
