package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.SimpleResponse;
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

           Long rank = zsetOperations.reverseRank("ranking-visit",tmpString); //몇위인지
           if(rank != null){
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
                return SimpleResponse.badRequest();
            }

            if(date.compareTo(time)  < 0){
                //이미지난채용이면 캐시에서 지우기
                zsetOperations.remove("ranking-visit",rank.getValue());
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
        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {

            String[] array = rank.getValue().split(":");

            String endTime = array[0];
            Date endDate = transFormat.parse(endTime);

            if(endDate.compareTo(time) <0 ) {
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

}
