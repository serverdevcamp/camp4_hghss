package com.rest.recruit.controller;

import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetCalendarResponse;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.exception.ExpiredTokenException;
import com.rest.recruit.exception.UnValidatedDateTypeException;
import com.rest.recruit.exception.UnauthorizedException;
import com.rest.recruit.service.RecruitService;
import com.rest.recruit.util.DateValidation;
import com.rest.recruit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.ParseException;

//또한, 특정 도메인만 접속을 허용할 수도 있습니다.
  //      - @CrossOrigin(origins = "허용주소:포트")

@CrossOrigin("*")
@Api(tags={"채용공고"})
@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    @Autowired
    private JwtUtil jwtUtil;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }


    @ApiOperation(value = "채용공고 캘린더 조회", httpMethod = "GET", notes = "채용공고 캘린더 조회" , response=GetCalendarResponse.class)
    @GetMapping("/calendar")
    public ResponseEntity calendar(@ApiParam(value = "startTime , endTime", required = true)
                                       @RequestHeader(value="Authorization", required=false) String token,
                                   @RequestParam(value = "startTime")  String startTime,
                                   @RequestParam(value = "endTime") String endTime) {



        if (!DateValidation.validationDate(startTime) || !DateValidation.validationDate(endTime)) {
            throw new UnValidatedDateTypeException();
        }

        if (token== null || token.isEmpty()) {
            return recruitService.GetRecruitCalendarByDate
                    (GetRecruitCalendarRequestDTO.builder().startTime(startTime).endTime(endTime).build());
        }



        String tokenString = token.substring("Bearer ".length());

        if(!jwtUtil.isAccessToken(tokenString)){
            throw new UnauthorizedException();
        }

        if(!jwtUtil.isValidToken(token)){
            throw new ExpiredTokenException();
        }

        String userIdx = Integer.toString(jwtUtil.getAuthentication(tokenString));

        return recruitService.GetRecruitCalendarByDate(GetRecruitCalendarRequestDTO.builder()
                .startTime(startTime).endTime(endTime)
                .userIdx(userIdx).build());


    }

    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회",response= GetRecruitDetailResponseDTO.class)
    @GetMapping("/detail/{recruitIdx}")
    public ResponseEntity detailRecuitPage(@ApiParam(value = "recruitIdx", required = true)
                                               @RequestHeader(value="Authorization", required=false) String token,
                                           @PathVariable(value = "recruitIdx") int recruitIdx) throws ParseException {
        if (token== null || token.isEmpty()) {
            return recruitService.GetDetailRecruitPage(DataWithToken.builder().recruitIdx(recruitIdx).build());
        }

        String tokenString = token.substring("Bearer ".length());

        if(!jwtUtil.isAccessToken(tokenString)){
            throw new UnauthorizedException();
        }

        if(!jwtUtil.isValidToken(token)){
            throw new ExpiredTokenException();
        }


        return recruitService.GetDetailRecruitPage(DataWithToken.builder()
                .userIdx(jwtUtil.getAuthentication(tokenString)).recruitIdx(recruitIdx).build());
    }

    @ApiOperation(value = "채용공고 즐겨찾기", httpMethod = "POST", notes = "채용공고 즐겨찾기",response= ResultResponseWithoutData.class)
    @PostMapping("/like/{recruitIdx}")
    public ResponseEntity likeRecuit(@ApiParam(value = "recruitIdx, token", required = true)
                                           @RequestHeader(value="Authorization") String token,
                                           @PathVariable(value = "recruitIdx") int recruitIdx) {

        String tokenString = token.substring("Bearer ".length());

        return recruitService.PostLikeRecruit(DataWithToken.builder()
                .userIdx(jwtUtil.getAuthentication(tokenString)).recruitIdx(recruitIdx).build());
    }

    @ApiOperation(value = "채용공고 즐겨찾기 취소", httpMethod = "DELETE", notes = "채용공고 즐겨찾기 취소",response= ResultResponseWithoutData.class)
    @DeleteMapping("/unlike/{recruitIdx}")
    public ResponseEntity unlikeRecuit(@ApiParam(value = "recruitIdx, token", required = true)
                                           @RequestHeader(value="Authorization") String token,
                                           @PathVariable(value = "recruitIdx") int recruitIdx) {

        String tokenString = token.substring("Bearer ".length());

        return recruitService.PostUnlikeRecruit(DataWithToken.builder()
                .userIdx(jwtUtil.getAuthentication(tokenString)).recruitIdx(recruitIdx).build());
    }
}

