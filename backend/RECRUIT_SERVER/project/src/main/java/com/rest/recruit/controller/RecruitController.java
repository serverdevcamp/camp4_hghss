package com.rest.recruit.controller;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetCalendarResponse;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.exception.UnValidatedDateTypeException;
import com.rest.recruit.exception.UnauthorizedException;
import com.rest.recruit.service.RecruitService;
import com.rest.recruit.util.DateValidation;
import com.rest.recruit.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//또한, 특정 도메인만 접속을 허용할 수도 있습니다.
//      - @CrossOrigin(origins = "허용주소:포트")

@CrossOrigin("*")
@Api(tags={"채용공고"})
@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;
    private static final Logger logger = LoggerFactory.getLogger(RecruitController.class);

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

        long start = System.currentTimeMillis();
        logger.info("채용공고캘린더 api 실행 시작");
        if (!DateValidation.validationDate(startTime) || !DateValidation.validationDate(endTime)) {

            throw new UnValidatedDateTypeException();
        }

        if (token== null || token.isEmpty() ) {
            ResponseEntity result = recruitService.GetRecruitCalendarByDate
                    (GetRecruitCalendarRequestDTO.builder().startTime(startTime).endTime(endTime).build());

            long end = System.currentTimeMillis();
            logger.info("채용공고캘린더의 Cache 수행시간 : "+ Long.toString(end-start));
            return result;
        }


        String tokenString = token.substring("Bearer ".length());
        if (!jwtUtil.isValidToken(tokenString)) {//expiration, 꺼내기

            ResponseEntity result = recruitService.GetRecruitCalendarByDate
                    (GetRecruitCalendarRequestDTO.builder().startTime(startTime).endTime(endTime).statusCode(402).build());

            long end = System.currentTimeMillis();
            logger.info("채용공고캘린더의 Cache 수행시간 : "+ Long.toString(end-start));
            return result;
        }

        if(!jwtUtil.isAccessToken(tokenString)){//refresh vs access
            throw new UnauthorizedException();
        }



        String userIdx = Integer.toString(jwtUtil.getAuthentication(tokenString));

        ResponseEntity result = recruitService.GetRecruitCalendarByDate(GetRecruitCalendarRequestDTO.builder()
                .startTime(startTime).endTime(endTime)
                .userIdx(userIdx).build());

        long end = System.currentTimeMillis();
        logger.info("채용공고캘린더의 Cache 수행시간 : "+ Long.toString(end-start));

        return result;
    }


    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회",response= GetRecruitDetailResponseDTO.class)
    @GetMapping("/detail/{recruitIdx}")
    public ResponseEntity detailRecuitPage(@ApiParam(value = "recruitIdx", required = true)
                                           @RequestHeader(value="Authorization", required=false) String token,
                                           @PathVariable(value = "recruitIdx") int recruitIdx) {
        logger.info("상세 채용공고 api 실행 시작");
        long start = System.currentTimeMillis();
        if (token== null || token.isEmpty()) {
            ResponseEntity result = recruitService.GetDetailRecruitPage(DataWithToken.builder().recruitIdx(recruitIdx).build());

            long end = System.currentTimeMillis();
            logger.info("채용공고캘린더의 Cache 수행시간 : "+ Long.toString(end-start));
            return result;
        }

        String tokenString = token.substring("Bearer ".length());

        if(!jwtUtil.isValidToken(tokenString)){
            logger.info("expire or no valid token\n");
            //throw new ExpiredTokenException();
            ResponseEntity result = recruitService.GetDetailRecruitPage(DataWithToken.builder().recruitIdx(recruitIdx).statusCode(402).build());
            long end = System.currentTimeMillis();
            logger.info("채용공고상세페이지의 Cache 수행시간 : "+ Long.toString(end-start));
            return result;
        }

        if(!jwtUtil.isAccessToken(tokenString)) {
            logger.info("no accessToken");
            throw new UnauthorizedException();
            //return recruitService.GetDetailRecruitPage(DataWithToken.builder().recruitIdx(recruitIdx).statusCode(401).build());
        }




        ResponseEntity result = recruitService.GetDetailRecruitPage(DataWithToken.builder()
                .userIdx(jwtUtil.getAuthentication(tokenString)).recruitIdx(recruitIdx).build());

        long end = System.currentTimeMillis();
        logger.info("채용공고상세페이지의 Cache 수행시간 : "+ Long.toString(end-start));
        return result;
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