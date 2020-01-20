package com.rest.recruit.controller;

import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.service.RecruitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags={"채용공고"})
@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }


    //email confirm
    @ApiOperation(value = "채용공고 캘린더 조회", httpMethod = "GET", notes = "채용공고 캘린더 조회")
    @GetMapping
    public ResponseEntity calendar(@ApiParam(value = "start_date , end_date", required = true) 
        @RequestBody GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO){
        return recruitService.GetRecruitCalendarByDate(getRecruitCalendarRequestDTO);
    }

    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회")
    @GetMapping("/detail/{recruitIdx}")
    public ResponseEntity detailRecuitPage(@ApiParam(value = "recruitIdx", required = true) 
    @PathVariable("recruitIdx") int recruitIdx){
        return recruitService.GetDetailRecruitPage(recruitIdx);
    }
}