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
import com.rest.recruit.model.Position;
import com.rest.recruit.model.Question;
import com.rest.recruit.model.RecruitDetail;
import com.rest.recruit.model.SimpleRecruit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

            //token
            //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsInVzZXJJZCI6OSwiZW1haWwiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsIm5pY2tuYW1lIjoidGVzdCIsInJvbGVzIjpbIlVTRVIiXSwidG9rZW5UeXBlIjoiQUNDRVNTX1RPS0VOIiwiZXhwIjoxNTc5NzYwOTYyfQ.U-0kZR0QgV9FSxws_n8V3GiYDdWiMDEwH9-zwCiB884

            if(!dataWithToken.getToken().isEmpty() || dataWithToken.getToken() != null) {

            }
            SimpleRecruit tmp = recruitMapper.getSimpleRecruitById(dataWithToken.getRecruitIdx());

            System.out.print("\n@@Companyname\n");
            System.out.print(tmp.getCompanyName());
            System.out.print("\n@@@@@@@@@\n");

            String tmpString = tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                    tmp.getCompanyId() + ":" + tmp.getCompanyName();

            ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();

            RecruitDetail tmpdetail = recruitMapper.GetDetailRecruitPage(dataWithToken.getRecruitIdx());
            List<Position> tmpPosition = recruitMapper.getPosition(dataWithToken.getRecruitIdx());
            List<Question> tmpQuestion = new ArrayList<>();

            System.out.print("\n@@pageurl\n");
            System.out.print(tmpdetail.getEmploymentPageUrl());
            System.out.print("\n@@@@@@@@@\n");

            if(zsetOperations.reverseRank("ranking-visit",tmpString) != null){
                double score = zsetOperations.incrementScore("ranking-visit",tmpString,1);
                tmpdetail.setViewCount( Integer.parseInt(String.valueOf(Math.round(score))));
            }else{

                tmpdetail.setViewCount(tmpdetail.getViewCount()+1);
                int updateCheck = recruitMapper.updateViewCountWithDB(dataWithToken.getRecruitIdx());
            //레디스에없다면 update +1
                if(updateCheck < 0){
                    return SimpleResponse.ok();
                }
            }

            for (int i = 0;i<tmpPosition.size();i++) {
                tmpQuestion.add(new Question(tmpPosition.get(i).getQuestionId(),tmpPosition.get(i).getQuestionContent()));
            }

            GetRecruitDetailResponseDTO getRecruitDetailResponseDTO
                    = new GetRecruitDetailResponseDTO(tmpdetail,GetRecruitPositionResponseDTO.of(tmpPosition.get(0),tmpQuestion));


            if (getRecruitDetailResponseDTO == null ) { throw new GetDetailRecruitPageException(); }

            return SimpleResponse.ok(ResultResponse.builder()
                    .message("상세 조회 성공")
                    .status("200")
                    .success("true")
                    .data(getRecruitDetailResponseDTO).build());



    }


}
