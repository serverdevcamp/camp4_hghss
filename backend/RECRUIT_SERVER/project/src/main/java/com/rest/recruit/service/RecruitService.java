package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetCalendarResponse;
import com.rest.recruit.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.dto.response.GetRecruitPositionResponseDTO;
import com.rest.recruit.exception.GetCalendarException;
import com.rest.recruit.exception.GetDetailRecruitPageException;
import com.rest.recruit.mapper.RecruitMapper;
import com.rest.recruit.model.*;
import com.rest.recruit.util.JwtUtil;
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
public class RecruitService {


    @Autowired
    RecruitMapper recruitMapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public ResponseEntity GetRecruitCalendarByDate(GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO) {


        List<GetRecruitCalendarSimpleResponseDTO> tmp = recruitMapper.getRecruitCalendarByDate(getRecruitCalendarRequestDTO);

        if (tmp.size() == 0 ||tmp.isEmpty()) { throw new GetCalendarException(); }

        List<GetCalendarResponse> results = new ArrayList<>();


        for (int i = 0; i < tmp.size(); i++) {
            results.add(GetCalendarResponse.of(tmp.get(i)));
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("캘린더 조회 성공")
                .status("200")
                .success("true")
                .data(results).build());

    }




    //채용공고 상세 페이지
    public ResponseEntity GetDetailRecruitPage(DataWithToken dataWithToken) {

        RecruitDetail tmpdetail = recruitMapper.GetDetailRecruitPage(dataWithToken);

        String tmpString = tmpdetail.getEndTime()+":"+tmpdetail.getRecruitId() + ":" +
                tmpdetail.getCompanyId() + ":" + tmpdetail.getCompanyName();

        ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();


        if (zsetOperations.reverseRank("ranking-visit",tmpString) != null) {
            double score = zsetOperations.incrementScore("ranking-visit",tmpString,1);
            tmpdetail.setViewCount( Integer.parseInt(String.valueOf(Math.round(score))));
        } else {

            tmpdetail.setViewCount(tmpdetail.getViewCount()+1);
            int updateCheck = recruitMapper.updateViewCountWithDB(dataWithToken.getRecruitIdx());
            //레디스에없다면 update +1
            if (updateCheck < 0) {
                return SimpleResponse.ok();
            }
        }

        List<Position> tmpPosition = recruitMapper.getPosition(dataWithToken.getRecruitIdx());
        List<Question> tmpQuestionList = new ArrayList<>();
        List<GetRecruitPositionResponseDTO> tmpEmployments = new ArrayList<>();


        int j = 0;
        for (int i = 1;i<tmpPosition.size();i++) {

            tmpQuestionList.add(new Question(tmpPosition.get(i).getQuestionId(),
                    tmpPosition.get(i).getQuestionContent(),tmpPosition.get(i).getQuestionLimit()));

            if (tmpPosition.get(i).getPositionId() != tmpPosition.get(i-1).getPositionId()) {

                tmpEmployments.add(new GetRecruitPositionResponseDTO(tmpPosition.get(i-1).getPositionId(),
                        tmpPosition.get(i-1).getField(),
                        tmpPosition.get(i-1).getDivision(),
                        tmpPosition.get(i-1).getQuestionId(),tmpQuestionList));
                j = i;
                tmpQuestionList = new ArrayList<>();
            }


        }

        tmpQuestionList = new ArrayList<>();
        for (int k = j;k<=tmpPosition.size()-1;k++) {
            tmpQuestionList.add(new Question(tmpPosition.get(k).getQuestionId(),
                    tmpPosition.get(k).getQuestionContent(),tmpPosition.get(k).getQuestionLimit()));
        }
        tmpEmployments.add(new GetRecruitPositionResponseDTO(tmpPosition.get(j).getPositionId(),
                tmpPosition.get(j).getField(),
                tmpPosition.get(j).getDivision(),
                tmpPosition.get(j).getQuestionId(),tmpQuestionList));

        GetRecruitDetailResponseDTO getRecruitDetailResponseDTO
                = new GetRecruitDetailResponseDTO(tmpdetail,tmpEmployments);

            getRecruitDetailResponseDTO.setFavorite(false);

        if (tmpdetail.getFavorite() > 0) {
            getRecruitDetailResponseDTO.setFavorite(true);
        }

        if (getRecruitDetailResponseDTO == null ) { throw new GetDetailRecruitPageException(); }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("상세 조회 성공")
                .status("200")
                .success("true")
                .data(getRecruitDetailResponseDTO).build());
    }

    public ResponseEntity PostUnlikeRecruit(DataWithToken dataWithToken) {

        RecruitLike tmp = recruitMapper.GetFavorite(dataWithToken.getUserIdx(),dataWithToken.getRecruitIdx());

        if(tmp != null){
            int  tmpdetail = recruitMapper.PostUnlikeRecruit(dataWithToken);
            int updateCount = recruitMapper.PostUnlikeRecruitCount(dataWithToken.getRecruitIdx());

            if(tmpdetail > 0 && updateCount > 0) {

                RecruitDetail tmpRedis = recruitMapper.GetDetailRecruitPage(dataWithToken);

                String tmpString = tmpRedis.getEndTime()+":"+tmpRedis.getRecruitId() + ":" +
                        tmpRedis.getCompanyId() + ":" + tmpRedis.getCompanyName();

                ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();


                if (zsetOperations.reverseRank("ranking-like",tmpString) != null) {
                    double score = zsetOperations.incrementScore("ranking-like",tmpString,-1);
                }


                return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채용공고 즐겨찾기 취소 성공")
                        .status("200")
                        .success("true")
                        .build());
            }else{
                return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채용공고 즐겨찾기 취소 실패")
                        .status("400")
                        .success("false")
                        .build());
            }
        }else{
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("채용공고 즐겨찾기 조회 실패")
                    .status("400")
                    .success("false")
                    .build());
        }

    }

    public ResponseEntity PostLikeRecruit(DataWithToken dataWithToken) {

        RecruitLike likeResult = recruitMapper.GetFavorite(dataWithToken.getUserIdx(),dataWithToken.getRecruitIdx());

        if (likeResult != null) {
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("이미 즐겨찾기하였습니다")
                    .status("200")
                    .success("true")
                    .build());
        }

        int tmpdetail = recruitMapper.PostLikeRecruit(dataWithToken);
        int updateCount = recruitMapper.PostLikeRecruitCount(dataWithToken.getRecruitIdx());


        if(tmpdetail > 0 && updateCount > 0){

            RecruitDetail tmpRedis = recruitMapper.GetDetailRecruitPage(dataWithToken);

            String tmpString = tmpRedis.getEndTime()+":"+tmpRedis.getRecruitId() + ":" +
                    tmpRedis.getCompanyId() + ":" + tmpRedis.getCompanyName();

            ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();


            if (zsetOperations.reverseRank("ranking-like",tmpString) != null) {
                double score = zsetOperations.incrementScore("ranking-like",tmpString,1);
            }


            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("채용공고 즐겨찾기 성공")
                    .status("200")
                    .success("true")
                    .build());
        }else{
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("채용공고 즐겨찾기 실패")
                    .status("400")
                    .success("false")
                    .build());
        }

    }
}
