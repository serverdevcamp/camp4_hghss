package com.smilegate.auth.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResultResponse {
    private String success;//true,false
    private String message;//"캘린더 조회 성공"
    private Integer status; //200
    private Object data;
}