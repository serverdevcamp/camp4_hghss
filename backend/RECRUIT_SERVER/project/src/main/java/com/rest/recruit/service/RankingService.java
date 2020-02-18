package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.response.GetRankingResponseDTO;
import com.rest.recruit.exception.RedisToDbException;
import com.rest.recruit.mapper.RecruitMapper;
import com.rest.recruit.model.SimpleRecruit;
import com.rest.recruit.util.RedisZsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class RankingService {

    @Autowired
    RecruitMapper recruitMapper;

    @Autowired
    RedisZsetUtil redisZsetUtil;


    public ResponseEntity DbToRedis() {

        List<SimpleRecruit> recruitDetail = recruitMapper.getSimpleRecruit();

        for (SimpleRecruit tmp : recruitDetail) {
            String tmpString =  tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                    tmp.getCompanyId() + ":" + tmp.getCompanyName();

            Long visitRank = redisZsetUtil.reverseRank("ranking-visit",tmpString);
            Long likeRank = redisZsetUtil.reverseRank("ranking-like",tmpString);
            Long applyRank = redisZsetUtil.reverseRank("ranking-apply",tmpString);

            if (visitRank != null || likeRank != null || applyRank != null) { continue; }

            System.out.print("\nredisTmp\n");
            System.out.print(tmpString);
            System.out.print("\n");

            redisZsetUtil.add("ranking-apply", tmpString, tmp.getApplyCount());
            redisZsetUtil.add("ranking-visit", tmpString, tmp.getViewCount());
            redisZsetUtil.add("ranking-like", tmpString, tmp.getFavoriteCount());
        }

        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("db to redis 성공")
                .status("200")
                .success("true")
                .build());


    }
    public ResponseEntity RedisToDb() throws ParseException {

        Set<ZSetOperations.TypedTuple<String>> rankingSet = redisZsetUtil
                .reverseRangeWithScores("ranking-visit", 0, -1);

        SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd-HH-mm");

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");


        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {
            String[] array = rank.getValue().split(":");
            String from = array[0];

            Date date = transFormat.parse(from);

            int tmp = recruitMapper.updateViewCount(Integer.parseInt(array[1]),
                    Integer.parseInt(String.valueOf(Math.round(rank.getScore()))));

            if (tmp < 0) {throw new RedisToDbException(); }

            if (date.compareTo(time)  < 0) {

                redisZsetUtil.remove("ranking-visit",rank.getValue());
                redisZsetUtil.remove("ranking-apply",rank.getValue());
                redisZsetUtil.remove("ranking-like",rank.getValue());
            }
        }

        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("redis to db 성공")
                .status("200")
                .success("true")
                .build());
    }


    public ResponseEntity getRankingByVisitCnt() throws ParseException {

        Set<ZSetOperations.TypedTuple<String>> rankingSet
                = redisZsetUtil.reverseRangeWithScores("ranking-visit", 0, 20);

        List<GetRankingResponseDTO> getRankingResponseDTOList = new ArrayList<>();

        int i = 1;

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DATE,30);

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            //오늘+7 이전에 마감해야함 = 마감일.compareTo(오늘+7) < 0
            //오늘이후에 마감해야함 = 오늘compareTo(마감일) < 0

//            if (endDate.compareTo(time) <0  || cal.getTime().compareTo(endDate) < 0) { continue; }


            if(time.compareTo(endDate) <= 0 && endDate.compareTo(cal.getTime()) <= 0){
                getRankingResponseDTOList
                        .add(new GetRankingResponseDTO(array,rank.getScore(),i++));
            }

            if (i > 5) { break; }

        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 조회수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }


    public ResponseEntity getRankingByLikeCnt() throws ParseException {

        Set<ZSetOperations.TypedTuple<String>> rankingSet
                = redisZsetUtil.reverseRangeWithScores("ranking-like", 0, 20);

        List<GetRankingResponseDTO> getRankingResponseDTOList = new ArrayList<>();
        int i = 1;

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DATE,30);

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            //7일 이후마감이면 제외

            if(time.compareTo(endDate) <= 0 && endDate.compareTo(cal.getTime()) <= 0){
                getRankingResponseDTOList
                        .add(new GetRankingResponseDTO(array,rank.getScore(),i++));
            }

            if (i > 5) { break;}
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 즐겨찾기 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }

    public ResponseEntity getRankingByApplyCnt() throws ParseException {

        Set<ZSetOperations.TypedTuple<String>> rankingSet =
                redisZsetUtil.reverseRangeWithScores("ranking-apply", 0, 20);

        List<GetRankingResponseDTO> getRankingResponseDTOList = new ArrayList<>();
        int i = 1;

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DATE,30);

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            //이미 마감 or 오 늘+ 7일 이전에 끝나지않을때
            if(time.compareTo(endDate) <= 0 && endDate.compareTo(cal.getTime()) <= 0){
                /*this.recruitId = Integer.parseInt(array[1]);
        this.companyId = Integer.parseInt(array[2]);
        this.companyName = array[3];
        this.count = count;
        this.rank  = rank;
        this.endTime = array[0];*/
                System.out.print("companyNmae\n");
                System.out.print(array[3]);
                getRankingResponseDTOList
                        .add(new GetRankingResponseDTO(array,rank.getScore(),i++));
            }

            if (i > 5) { break; }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 지원자수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }
}