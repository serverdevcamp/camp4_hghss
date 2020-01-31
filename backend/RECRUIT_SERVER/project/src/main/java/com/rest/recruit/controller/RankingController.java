package com.rest.recruit.controller;


import com.rest.recruit.dto.response.GetRankingResponseDTO;
import com.rest.recruit.service.RankingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin("*")
@Api(tags={"채용공고 - 랭킹"})
@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    //먼저해야함
    //매일오후 11시 50분
    @Scheduled(cron="0 50 23 * * ?")
    @ApiOperation(value = "채용공고 db to redis", httpMethod = "POST", notes ="채용공고 DB를 REDIS에 업로드")
    @PostMapping("/updateRedis")
    public ResponseEntity DbToRedis(){
        return rankingService.DbToRedis();
    }


    //스케쥴링
    //매일정각
    @Scheduled(cron="0 0 0 * * ?")
    @ApiOperation(value = "redis to 채용공고 db", httpMethod = "PUT", notes ="REDIS를 채용공고 DB에 업로드")
    @PostMapping("/updateDB")
    public ResponseEntity RedisToDb() throws ParseException {
        return rankingService.RedisToDb();
    }

    @ApiOperation(value = "7일내 마감하는 조회수 랭킹", httpMethod = "GET", notes ="7일내 마감하는 조회수 랭킹",
            response= GetRankingResponseDTO.class)
    @GetMapping("/visit")
    public ResponseEntity getRankingByVisitCnt() throws ParseException {
        return rankingService.getRankingByVisitCnt();
    }

    @ApiOperation(value = "7일내 마감하는 즐겨찾기 랭킹", httpMethod = "GET", notes ="7일내 마감하는 즐겨찾기 랭킹",
            response= GetRankingResponseDTO.class)
    @GetMapping("/like")
    public ResponseEntity getRankingByLikeCnt() throws ParseException {
        return rankingService.getRankingByLikeCnt();
    }

    @ApiOperation(value = "7일내 마감하는 지원자 수 랭킹", httpMethod = "GET", notes ="7일내 마감하는 지원자 수 랭킹",
            response= GetRankingResponseDTO.class)
    @GetMapping("/apply")
    public ResponseEntity getRankingByApplyCnt() throws ParseException {
        return rankingService.getRankingByApplyCnt();
    }
}
