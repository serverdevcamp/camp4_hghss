package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.response.GetRankingByLikeCntResponseDTO;
import com.rest.recruit.dto.response.GetRankingByVisitCntResponseDTO;
import com.rest.recruit.mapper.RecruitMapper;
import com.rest.recruit.model.SimpleRecruit;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.ClassFileLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.gv.LocaleNames_gv;

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
            Long visitRank = zsetOperations.reverseRank("ranking-visit",tmpString); //몇위인지
            Long likeRank = zsetOperations.reverseRank("ranking-like",tmpString); //몇위인지

            if(visitRank != null || likeRank != null){
               //이미 존재한다면
               continue;
           }

            zsetOperations.add("ranking-visit", tmpString, tmp.getViewCount());
            zsetOperations.add("ranking-like", tmpString, tmp.getFavoriteCount());
        }



        return SimpleResponse.ok();
    }

    public ResponseEntity RedisToDb() throws ParseException {

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

            //db업데이트
            int tmp = recruitMapper.updateViewCount(Integer.parseInt(array[1]),
                    Integer.parseInt(String.valueOf(Math.round(rank.getScore()))));

            if(tmp < 0){
//simpleResponse.ok
                return SimpleResponse.badRequest();
            }

            if(date.compareTo(time)  < 0){
                //이미지난채용이면 캐시에서 지우기
                zsetOperations.remove("ranking-visit",rank.getValue());
            }


        }

        //좋아요 랭킹시 remove하는 경우:레디스에있는 cnt를 넣을 때 이미 endTIme이 지난 건 레디스에서 삭제
        Set<ZSetOperations.TypedTuple<String>> likeRankingSet = zsetOperations
                .reverseRangeWithScores("ranking-like", 0, -1);

        for (ZSetOperations.TypedTuple<String> rank : likeRankingSet) {
            String[] array = rank.getValue().split(":");
            String from = array[0];
            Date date = transFormat.parse(from);

            if(date.compareTo(time)  < 0){
                //이미지난채용이면 캐시에서 지우기
                zsetOperations.remove("ranking-like",rank.getValue());
            }
        }



        return SimpleResponse.ok();
    }


    public ResponseEntity getRankingByVisitCnt() throws ParseException {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("ranking-visit", 0, 9);

        List<GetRankingByVisitCntResponseDTO> getRankingByVisitCntResponseDTOList = new ArrayList<>();
        int i = 1;

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DATE,7);

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            //7일 이후마감이면 제외
            if(endDate.compareTo(time) <0  || endDate.compareTo(cal.getTime()) > 0) {
                continue;
            }

            getRankingByVisitCntResponseDTOList
                        .add(new GetRankingByVisitCntResponseDTO(array,rank.getScore(),i++));

            if(i > 5){
                break;
            }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 조회수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingByVisitCntResponseDTOList).build());
    }

    public ResponseEntity getRankingByLikeCnt() throws ParseException {
//7일 내 마감하는 즐겨찾기 랭킹
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("ranking-like", 0, 9);

        List<GetRankingByLikeCntResponseDTO> getRankingByLikeCntResponseDTOList = new ArrayList<>();
        int i = 1;

        Date time = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.DATE,7);

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            //7일이후마감하면
            if(endDate.compareTo(time) <0 || endDate.compareTo(cal.getTime()) > 0) {
                continue;
            }

            getRankingByLikeCntResponseDTOList
                    .add(new GetRankingByLikeCntResponseDTO(array,rank.getScore(),i++));

            if(i > 5){
                break;
            }
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 즐겨찾기 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingByLikeCntResponseDTOList).build());

    }
}
