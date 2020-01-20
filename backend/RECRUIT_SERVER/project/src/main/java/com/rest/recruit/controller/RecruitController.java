package com.rest.recruit.controller;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.service.RecruitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

/*    !validationDate(getRecruitCalendarRequestDTO.getStartTime()) ||
                !validationDate(getRecruitCalendarRequestDTO.getEndTime())*/
        if ( getRecruitCalendarRequestDTO.getEndTime() == null ||
                getRecruitCalendarRequestDTO.getStartTime() == null ||
                getRecruitCalendarRequestDTO.getEndTime().isEmpty() ||
                getRecruitCalendarRequestDTO.getStartTime().isEmpty() ||
                !validationDate(getRecruitCalendarRequestDTO.getStartTime()) ||
                !validationDate(getRecruitCalendarRequestDTO.getEndTime())) {

            return SimpleResponse.badRequest(ResultResponseWithoutData.builder()
                    .message("필요한 값이 잘못되었습니다")
                    .status("400")
                    .success("false").build());

        }


        return recruitService.GetRecruitCalendarByDate(getRecruitCalendarRequestDTO);
    }

    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회")
    @GetMapping("/detail/{recruitIdx}")
    public ResponseEntity detailRecuitPage(@ApiParam(value = "recruitIdx", required = true) 
    @PathVariable("recruitIdx") int recruitIdx){

        return recruitService.GetDetailRecruitPage(recruitIdx);
    }


    private boolean validationDate(String checkDate) {

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            dateFormat.setLenient(false);
            dateFormat.parse(checkDate);
            return  true;

        }catch (ParseException e){
            return  false;
        }

    }


}