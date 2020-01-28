package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleRecruit {
    private int recruitId;
    private int companyId;
    private String endTime;
    private String companyName;
    private int viewCount;
    private int favoriteCount;
    private int applyCount;
}
