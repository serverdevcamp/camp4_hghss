package com.rest.user.controller;

import com.rest.user.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.user.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.user.service.RecruitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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