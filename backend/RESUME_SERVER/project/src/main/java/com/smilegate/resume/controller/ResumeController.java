package com.smilegate.resume.controller;

import com.smilegate.resume.domain.Answer;
import com.smilegate.resume.domain.Resume;
import com.smilegate.resume.dto.ResultResponse;
import com.smilegate.resume.dto.request.ResumeRequestDto;
import com.smilegate.resume.dto.response.ResumeCreateResponseDto;
import com.smilegate.resume.dto.response.ResumeDetailResponseDto;
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

    @PostMapping("/create/{positionId}")
    public ResponseEntity<ResultResponse> create(
            @RequestHeader("Authorization") String token,
            @PathVariable("positionId") int positionId,
            @RequestBody ResumeRequestDto resumeRequestDto
    ) {
        int resumeId = resumeService.createResume(token, positionId, resumeRequestDto);

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
    public ResponseEntity<ResultResponse> list(@RequestHeader("Authorization") String token) {

        List<Resume> resumes = resumeService.getResumes(token);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서 목록입니다.")
                        .data(resumes)
                        .build()
        );
    }

    @PutMapping("/save/{resumeId}")
    public ResponseEntity<ResultResponse> save(
            @RequestHeader("Authorization") String token,
            @PathVariable("resumeId") int resumeId,
            @RequestBody ResumeRequestDto resumeRequestDto
    ) {

        boolean success = resumeService.saveResume(resumeId, token, resumeRequestDto.getTitle(), resumeRequestDto.getAnswers());

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(success)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서를 저장했습니다.")
                        .build()
        );
    }

    @DeleteMapping("/delete/{resumeId}")
    public ResponseEntity<ResultResponse> deleteResume(
            @RequestHeader("Authorization") String token,
            @PathVariable("resumeId") int resumeId
    ) {

        boolean success = resumeService.deleteResume(resumeId, token);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(success)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서를 삭제했습니다.")
                        .build()
        );
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity<ResultResponse> moveResume(
            @RequestHeader("Authorization") String token,
            @PathVariable int resumeId,
            @RequestParam("col") int col,
            @RequestParam("row") int row
    ) {

        boolean success = resumeService.moveResume(resumeId, token, col, row);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(success)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서를 이동했습니다.")
                        .build()
        );
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<ResultResponse> detail(
            @RequestHeader("Authorization") String token,
            @PathVariable("resumeId") int resumeId
    ) {

        ResumeDetailResponseDto resumeDetailResponseDto = resumeService.getResume(token, resumeId);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("자기소개서입니다. : resume_id = " + resumeId)
                        .data(resumeDetailResponseDto)
                        .build()
        );
    }

    @PostMapping("/answer/add/{resumeId}")
    public ResponseEntity<ResultResponse> createAnswer(
            @RequestHeader("Authorization") String token,
            @PathVariable int resumeId
    ) {

        Answer answer = resumeService.createAnswer(token, resumeId);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("문항을 추가했습니다.")
                        .data(answer)
                        .build()
        );
    }

    @DeleteMapping("/answer/delete/{answerId}")
    public ResponseEntity<ResultResponse> deleteAnswer(
            @RequestHeader("Authorization") String token,
            @PathVariable int answerId
    ) {

        resumeService.deleteAnswer(token, answerId);

        return ResponseEntity.ok().body(
                ResultResponse.builder()
                        .success(true)
                        .status(HttpStatus.OK.value())
                        .message("문항을 삭제했습니다.")
                        .build()
        );
    }

}
