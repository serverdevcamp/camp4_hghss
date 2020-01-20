package com.rest.recruit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ResultResponse {
	private String success;//true,false
	private String message;//"캘린더 조회 성공"
	private String status; //200
	private Object data;

}