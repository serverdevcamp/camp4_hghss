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
    private List<GetRecruitPositionResponseDTO> employments;



    public GetRecruitDetailResponseDTO(RecruitDetail tmpdetail, List<GetRecruitPositionResponseDTO> tmpEmployments) {
        this.recruitId = tmpdetail.getRecruitId();
        this.companyName = tmpdetail.getCompanyName();
        this.imageFileName = tmpdetail.getImageFileName();
        this.employmentPageUrl = tmpdetail.getEmploymentPageUrl();
        this.startTime = tmpdetail.getStartTime();
        this.endTime = tmpdetail.getEndTime();
        this.recruitType = tmpdetail.getRecruitType();
        this.content = tmpdetail.getContent();
        this.viewCount = tmpdetail.getViewCount();
        this.favoriteCount = tmpdetail.getFavoriteCount();
        this.employments = tmpEmployments;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
