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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RankingService {

    @Autowired
    RecruitMapper recruitMapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public ResponseEntity DbToRedis() {

        //회사들의 CNT를 DB에 넣기
// //zset zSetOperations.add("t기est:user:kingbbode:wish", "배포한 것에 장애없길", 1);
        // zSetOperations.incrementScore("test:user:kingbbode:wish", "경력직 채용", -1);


        List<SimpleRecruit> recruitDetail = recruitMapper.getSimpleRecruit();
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();


        for (SimpleRecruit tmp : recruitDetail) {
            String tmpString = "ranking:" + tmp.getRecruitId() + ":" +
                    tmp.getCompanyId() + ":" + tmp.getCompanyName()+":"+tmp.getEndTime();
            zsetOperations.add("redis-visit", tmpString, tmp.getViewCount());
            zsetOperations.add("redis-like", tmpString, tmp.getFavoriteCount());

        }


        return SimpleResponse.ok();
    }

    public ResponseEntity RedisToDb() {


        //redis에서 recruitId와 cnt찾은 후
        //update Cnt

//        recruitMapper.updateRecruitCnt(
//        /
/*   <update id="updateRecruitCnt" useGeneratedKeys="true" parameterType="com.rest.recruit.model.RecruitDetail">
        UPDATE recruit SET  recruit.view_count = recruit.view_count + 1
        WHERE recruit.id = #{recruitIdx};
    </update>*/


        return SimpleResponse.ok();
    }


    public ResponseEntity getRankingByVisitCnt() {
        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rankingSet = zsetOperations.reverseRangeWithScores("redis-visit", 0, 4);


        List<GetRankingByVisitCntResponseDTO> getRankingByVisitCntResponseDTOList = new ArrayList<>();

        for (ZSetOperations.TypedTuple<String> rank : rankingSet) {
            String[] array = rank.getValue().split(":");
            getRankingByVisitCntResponseDTOList
                    .add(new GetRankingByVisitCntResponseDTO(array,rank.getScore()));
        }




        return SimpleResponse.ok(ResultResponse.builder()
                .message("7일내 조회수 랭킹 조회 성공")
                .status("200")
                .success("true")
                .data(getRankingByVisitCntResponseDTOList).build());
    }

}
