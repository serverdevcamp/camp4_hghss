package com.smilegate.resume.controller;

import com.smilegate.resume.domain.ResumeInfo;
import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.dto.response.ResumeCreateResponseDto;
import com.smilegate.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/create")
    public ResponseEntity<ResultResponse> create(
            @RequestParam("userId") int userId,
            @RequestParam("positionId") int positionId,
            @RequestBody ResumeRequestDto resume
    ) {
        int resumeId = resumeService.create(userId, positionId, resume);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.CREATED.value())
                        .message("자기소개서를 생성했습니다.")
                        .data(ResumeCreateResponseDto.builder().resumeId(resumeId).build())
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ResultResponse> list(
            @RequestParam("userId") int userId,
            @RequestParam("start") String start,
            @RequestParam("end") String end
    ) {

        List<ResumeInfo> resumes = resumeService.getList(userId, start, end);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서 목록입니다.")
                        .data(resumes)
                        .build()
        );
    }

}
