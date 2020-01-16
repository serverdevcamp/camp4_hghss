package com.rest.recruit.controller;

import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.service.RecruitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }


    //email confirm
    @GetMapping
    public ResponseEntity calendar(@RequestBody GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO){
        return recruitService.GetRecruitCalendarByDate(getRecruitCalendarRequestDTO);
    }
}