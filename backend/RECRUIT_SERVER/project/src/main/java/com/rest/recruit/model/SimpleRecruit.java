package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleRecruit {

    //companyId,recruitId,visitCnt,favoriteCnt,자소서작성수
    private int recruitId;
    private int companyId;
    private String endTime;
    private int viewCount;
    private int favoriteCount;
    private String companyName;


}
