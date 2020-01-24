package com.rest.recruit.exception;

public class GetDetailRecruitPageException extends RuntimeException{
    public GetDetailRecruitPageException() {
        super("상세 채용공고 조회 에러");
    }

}

