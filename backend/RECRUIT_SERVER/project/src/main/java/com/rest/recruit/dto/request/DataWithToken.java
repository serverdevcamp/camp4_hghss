package com.rest.recruit.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DataWithToken {
    private int recruitIdx;
    private int userIdx;
    private int statusCode;
}
