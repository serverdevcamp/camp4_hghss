package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recruit {
    private int recruitId;
    private int companyId;
    private String companyName;
    private String imageFileName;
    private String employmentPageUrl;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String content;
    private int viewCount;
    private boolean favorite;
    private int favoriteCount;
}
