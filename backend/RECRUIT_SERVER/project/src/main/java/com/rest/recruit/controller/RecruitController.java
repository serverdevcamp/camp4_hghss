package com.rest.recruit.controller;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetCalendarResponse;
import com.rest.recruit.dto.response.GetRankingByVisitCntResponseDTO;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.service.RankingService;
import com.rest.recruit.service.RecruitService;
import com.rest.recruit.util.DateValidation;
import lombok.Builder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

//또한, 특정 도메인만 접속을 허용할 수도 있습니다.
  //      - @CrossOrigin(origins = "허용주소:포트")

@CrossOrigin("*")
@Api(tags={"채용공고"})
@RestController
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    private final RankingService rankingService;


    public RecruitController(RecruitService recruitService,RankingService rankingService) {
        this.rankingService = rankingService;
        this.recruitService = recruitService;
    }


    /*   @PostMapping
    public simpleResponse insertExternalService(@RequestBody Map<String,Object> param) {
        String name = (String)param.get("name");
        String url = (String)param.get("url");*/
/*@RequestBody GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO*/
    //List<GetCalendarResponse> results
    @ApiOperation(value = "채용공고 캘린더 조회", httpMethod = "GET", notes = "채용공고 캘린더 조회" , response=GetCalendarResponse.class)
    @GetMapping
    public ResponseEntity calendar(@ApiParam(value = "start_date , end_date", required = true)
                                   @RequestParam(value = "startTime" ,required = false) String startTime,
                                   @RequestParam(value = "endTime",required = false) String endTime){

        if ( endTime == null ||
                startTime == null ||
                endTime.isEmpty() ||
                startTime.isEmpty() ||
                !DateValidation.validationDate(startTime) ||
                !DateValidation.validationDate(endTime)) {

            System.out.print("test\n");
            System.out.print("false");
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("필요한 값이 잘못되었습니다")
                    .status("400")
                    .success("false").build());
        }

/*
        if(token != null || !token.isEmpty()){
            //return recruitService.GetRecruitCalendarByDateWithToken(getRecruitCalendarRequestDTO,token);
        }
  */
        return recruitService.GetRecruitCalendarByDate(new GetRecruitCalendarRequestDTO(startTime,endTime));
    }

    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회",response= GetRecruitDetailResponseDTO.class)
    @GetMapping("/detail/{recruitIdx}")
    public ResponseEntity detailRecuitPage(@ApiParam(value = "recruitIdx", required = true)
                                               @RequestHeader(value="Authorization", required=false) String token,
                                           @PathVariable(value = "recruitIdx", required=false) int recruitIdx){

        if ( recruitIdx <= 0) {
            System.out.print("test\n");
            System.out.print("false");
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("필요한 값이 잘못되었습니다")
                    .status("400")
                    .success("false").build());
        }

        if(token== null || token.isEmpty()){
            return recruitService.GetDetailRecruitPage(DataWithToken.builder().recruitIdx(recruitIdx).build());
        }

        String tokenString = token.substring("Bearer ".length());

        return recruitService.GetDetailRecruitPage(DataWithToken.builder().token(tokenString).recruitIdx(recruitIdx).build());
    }

    //스케쥴링
    //일요일 정각
    @Scheduled(cron="0 0 0 * * 0")
    @ApiOperation(value = "채용공고 db to redis", httpMethod = "POST", notes ="채용공고 DB를 REDIS에 업로드")
    @PostMapping("/ranking/updateRedis")
    public ResponseEntity DbToRedis(){
        return rankingService.DbToRedis();
    }


    //스케쥴링
    //매일정각
    @Scheduled(cron="0 0 0 * * ?")
    @ApiOperation(value = "redis to 채용공고 db", httpMethod = "PUT", notes ="REDIS를 채용공고 DB에 업로드")
    @PostMapping("/ranking/updateDB")
    public ResponseEntity RedisToDb() throws ParseException {
        return rankingService.RedisToDb();
    }


//    @ApiOperation(value = "상세 채용공고 페이지 조회", httpMethod = "GET", notes = "상세 채용공고 페이지 조회",response= GetRecruitDetailResponseDTO.class)
    @ApiOperation(value = "7일내 마감하는 조회수 랭킹", httpMethod = "GET", notes ="7일내 마감하는 조회수 랭킹",response= GetRankingByVisitCntResponseDTO.class)
    @GetMapping("/ranking/visit")
    public ResponseEntity getRankingByVisitCnt() throws ParseException {
        return rankingService.getRankingByVisitCnt();
    }




}

