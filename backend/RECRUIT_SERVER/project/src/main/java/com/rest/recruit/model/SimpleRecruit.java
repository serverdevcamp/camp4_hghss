package com.rest.recruit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleRecruit {
    /*        recruit.id AS recruitId,
        recruit.company_id AS companyId,
        DATE_FORMAT(recruit.end_time,'%Y-%m-%d-%h-%i') AS endTime,
        company.name AS companyName
*/
    //companyId,recruitId,visitCnt,favoriteCnt,자소서작성수
    private int recruitId;
    private int companyId;
    private String endTime;
    //companyname?
    private String companyName;
    private int viewCount;
    private int favoriteCount;




}
