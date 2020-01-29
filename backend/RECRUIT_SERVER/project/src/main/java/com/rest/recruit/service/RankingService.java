package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.response.GetRankingResponseDTO;
import com.rest.recruit.exception.RedisToDbException;
import com.rest.recruit.mapper.RecruitMapper;
import com.rest.recruit.model.SimpleRecruit;
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
    RedisTemplate<String, String> redisTemplate;

    public ResponseEntity DbToRedis() {

        List<SimpleRecruit> recruitDetail = recruitMapper.getSimpleRecruit();

        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();

        for(SimpleRecruit tmp : recruitDetail){
            String tmpString =  tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                    tmp.getCompanyId() + ":" + tmp.getCompanyName();
            Long visitRank = zsetOperations.reverseRank("ranking-visit",tmpString);
            Long likeRank = zsetOperations.reverseRank("ranking-like",tmpString);
            Long applyRank = zsetOperations.reverseRank("ranking-apply",tmpString);

            if(visitRank != null || likeRank != null || applyRank != null){
               //이미 존재한다면
               continue;
           }

            zsetOperations.add("ranking-apply", tmpString, tmp.getApplyCount());
            zsetOperations.add("ranking-visit", tmpString, tmp.getViewCount());
            zsetOperations.add("ranking-like", tmpString, tmp.getFavoriteCount());
        }

        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("db to redis 성공")
                .status("200")
                .success("true")
                .build());


    }

    public ResponseEntity RedisToDb() throws ParseException {

        //좋아요 랭킹시 remove하는 경우:레디스에있는 cnt를 넣을 때 이미 endTIme이 지난 건 레디스에서 삭제
        //자기소개서 랭킹시 remove!!!

        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations
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

            //db viewCount update fail
            if (tmp < 0) {throw new RedisToDbException(); }

            if (date.compareTo(time)  < 0) {
                //이미지난채용이면 캐시에서 지우기
                zsetOperations.remove("ranking-visit",rank.getValue());
                zsetOperations.remove("ranking-apply",rank.getValue());
                zsetOperations.remove("ranking-like",rank.getValue());
            }
        }

        return SimpleResponse.ok(ResultResponseWithoutData.builder()
                .message("redis to db 성공")
                .status("200")
                .success("true")
                .build());
    }


    public ResponseEntity getRankingByVisitCnt() throws ParseException {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("ranking-visit", 0, 9);

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
            if(endDate.compareTo(time) <0  || endDate.compareTo(cal.getTime()) > 0) {
                continue;
            }

            getRankingResponseDTOList
                        .add(new GetRankingResponseDTO(array,rank.getScore(),i++));

            if(i > 5){
                break;
            }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 조회수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }


    public ResponseEntity getRankingByLikeCnt() throws ParseException {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("ranking-like", 0, 9);

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
            if(endDate.compareTo(time) <0  || endDate.compareTo(cal.getTime()) > 0) {
                continue;
            }

            System.out.print("\ntest\n");
            System.out.print(rank.getValue());

            System.out.print("\ntest\n");
            System.out.print(array);

            getRankingResponseDTOList
                    .add(new GetRankingResponseDTO(array,rank.getScore(),i++));

            if(i > 5){
                break;
            }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 즐겨찾기 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }

    public ResponseEntity getRankingByApplyCnt() throws ParseException {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("ranking-apply", 0, 9);

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
            if(endDate.compareTo(time) <0  || endDate.compareTo(cal.getTime()) > 0) {
                continue;
            }

            getRankingResponseDTOList
                    .add(new GetRankingResponseDTO(array,rank.getScore(),i++));

            if(i > 5){
                break;
            }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 지원자수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingResponseDTOList).build());
    }
}
