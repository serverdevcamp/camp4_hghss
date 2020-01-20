package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetCalendarResponse;
import com.rest.recruit.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.dto.response.GetRecruitPositionResponseDTO;
import com.rest.recruit.mapper.RecruitMapper;
import com.rest.recruit.model.Position;
import com.rest.recruit.model.Question;
import com.rest.recruit.model.RecruitDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    public ResponseEntity GetRecruitCalendarByDate(GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO) {

        List<GetRecruitCalendarSimpleResponseDTO> tmp = recruitMapper.getRecruitCalendarByDate(getRecruitCalendarRequestDTO);

        if (tmp.size() == 0 ||tmp.isEmpty()) {

            return SimpleResponse.ok(ResultResponse.builder()
                    .message("캘린더 조회 에러")
                    .status("500")
                    .success("false").build());
        }

        List<GetCalendarResponse> results = new ArrayList<>();
        for (int i = 0;i<tmp.size();i++) {
            results.add(GetCalendarResponse.of(tmp.get(i)));
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("캘린더 조회 성공")
                .status("200")
                .success("true")
                .data(results).build());
    }


    //채용공고 상세 페이지
    public ResponseEntity GetDetailRecruitPage(int recruitIdx) {

        try {

            int updateCnt = recruitMapper.updateViewCount(recruitIdx);
            RecruitDetail tmp = recruitMapper.GetDetailRecruitPage(recruitIdx);


            List<Position> tmpPosition = recruitMapper.getPosition(recruitIdx);
            List<Question> tmpQuestion = new ArrayList<>();

            for (int i = 0;i<tmpPosition.size();i++) {
                tmpQuestion.add(new Question(tmpPosition.get(i).getQuestionId(),tmpPosition.get(i).getQuestionContent()));
            }

            GetRecruitDetailResponseDTO getRecruitDetailResponseDTO
                    = new GetRecruitDetailResponseDTO(tmp,GetRecruitPositionResponseDTO.of(tmpPosition.get(0),tmpQuestion));


            return SimpleResponse.ok(ResultResponse.builder()
                    .message("상세 조회 성공")
                    .status("200")
                    .success("true")
                    .data(getRecruitDetailResponseDTO).build());



        } catch (Exception e) {

            return SimpleResponse.badRequest(ResultResponseWithoutData.builder()
                    .message("상세 채용공고 조회 에러")
                    .status("500")
                    .success("false").build());
        }


    }
}
