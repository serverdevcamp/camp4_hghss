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
        Set<ZSetOperations.TypedTuple<String>> rankingSet =
                zsetOperations.reverseRangeWithScores("redis-visit", 0, -1);

        if(rankingSet.size() == 0){
                for(SimpleRecruit tmp : recruitDetail){
                    System.out.print("tmp!\n");
                    System.out.print("Inter\n");
                    System.out.print(tmp.getRecruitId());
                        String tmpString = "ranking:" + tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                                tmp.getCompanyId() + ":" + tmp.getCompanyName();
                        zsetOperations.add("redis-visit", tmpString, tmp.getViewCount());
                        zsetOperations.add("redis-like", tmpString, tmp.getFavoriteCount());
                }

            return SimpleResponse.ok(rankingSet);
        }

            for (ZSetOperations.TypedTuple<String> rank : rankingSet) {
                for(SimpleRecruit tmp : recruitDetail){
                System.out.print("tmp!\n");
                String[] array = rank.getValue().split(":");

                System.out.print("Inter\n");
                System.out.print(tmp.getRecruitId());
                if(tmp.getRecruitId() != Integer.parseInt(array[2])){

                    String tmpString = "ranking:" + tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                            tmp.getCompanyId() + ":" + tmp.getCompanyName();
                    zsetOperations.add("redis-visit", tmpString, tmp.getViewCount());
                    zsetOperations.add("redis-like", tmpString, tmp.getFavoriteCount());
                }
            }
        }



        return SimpleResponse.ok();
    }

    public ResponseEntity RedisToDb() throws ParseException {


        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations
                .reverseRangeWithScores("redis-visit", 0, -1);
        SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd-HH-mm");
        Date time = new Date();

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {
            String[] array = rank.getValue().split(":");
            //String tmpString = "ranking:" + tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
            //                    tmp.getCompanyId() + ":" + tmp.getCompanyName();
            String from = array[1];
            //ss????
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
            Date date = transFormat.parse(from);
//Integer.parseInt(String.valueOf(Math.round(rank.getScore())))

            //db업데이트
            int tmp = recruitMapper.updateViewCount(Integer.parseInt(array[2]),
                    Integer.parseInt(String.valueOf(Math.round(rank.getScore()))));

            if(tmp < 0){
                return SimpleResponse.badRequest();
            }

            if(date.compareTo(time)  < 0){
                zsetOperations.remove("redis-visit",rank.getValue());
            }


        }

        return SimpleResponse.ok();
    }


    public ResponseEntity getRankingByVisitCnt() throws ParseException {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("redis-visit", 0, 4);

        List<GetRankingByVisitCntResponseDTO> getRankingByVisitCntResponseDTOList = new ArrayList<>();
        int i = 1;

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {


            System.out.print("\nrank\n");
            System.out.print(rank.getValue());
            String[] array = rank.getValue().split(":");
//Integer.parseInt(String.valueOf(Math.round(score))))

                getRankingByVisitCntResponseDTOList
                        .add(new GetRankingByVisitCntResponseDTO(array,rank.getScore(),i++));

        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 조회수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingByVisitCntResponseDTOList).build());
    }

}
