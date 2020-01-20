package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
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

import java.util.*;

@Slf4j
@Service
public class RecruitService {


    @Autowired
    RecruitMapper recruitMapper;


    public ResponseEntity GetRecruitCalendarByDate(GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO) {

        List<GetRecruitCalendarSimpleResponseDTO> tmp = recruitMapper.getRecruitCalendarByDate(getRecruitCalendarRequestDTO);
        List<Integer> employTypeTmp= new ArrayList<>();
        List<GetCalendarResponse> results = new ArrayList<>();

        int j = 0;
        for (int i = 1;i<tmp.size();i++) {

            employTypeTmp.add(tmp.get(i-1).getEmployType());//1,3,2

            if (tmp.get(i).getRecruitId()!= tmp.get(i-1).getRecruitId()) {
                System.out.print("\nemploytTYpetest\n");
                System.out.print(employTypeTmp);
                results.add(new GetCalendarResponse(tmp.get(i-1).getRecruitId(),
                        tmp.get(i-1).getCompanyId(),
                        tmp.get(i-1).getCompanyName(),
                        tmp.get(i-1).getImageFileName(),
                        tmp.get(i-1).getRecruitType(),
                        tmp.get(i-1).getStartTime(),
                        tmp.get(i-1).getEndTime(),
                        employTypeTmp));

                employTypeTmp= new ArrayList<>();
                j = i;
                System.out.print("\njTest\n");
                System.out.print(j);
            }

        }

        for (int k = j;k<=tmp.size()-1;k++) {
            employTypeTmp.add(tmp.get(k).getEmployType());
        }

        results.add(new GetCalendarResponse(tmp.get(j).getRecruitId(),
                tmp.get(j).getCompanyId(),
                tmp.get(j).getCompanyName(),
                tmp.get(j).getImageFileName(),
                tmp.get(j).getRecruitType(),
                tmp.get(j).getStartTime(),
                tmp.get(j).getEndTime(),
                employTypeTmp));

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setMessage("캘린더 조회 성공");
        resultResponse.setStatus("200");
        resultResponse.setSuccess("true");
        resultResponse.setData(results);
        return SimpleResponse.ok(resultResponse);
    }

//채용공고 상세 페이지
    public ResponseEntity GetDetailRecruitPage(int recruitIdx) {


/*
    private int recruitId;
    private String companyName;
    private String imageFileName;
    private String employmentPageUrl;
    private String startTime;
    private String endTime;
    private int recruitType;
    private String content
    private int viewCount;

    private int favoriteCount;}
*/
        RecruitDetail tmp = recruitMapper.GetDetailRecruitPage(recruitIdx);

        //list<position>형
        /*
        * <position>
        *
        * private int positionId;
    private String field;
    private int division;
    private int questionId;
    private String questionContent;
    //employMentResume
        *
    * * */

        List<Position> tmpPosition = recruitMapper.getPosition(recruitIdx);
        List<Question> tmpQuestion = new ArrayList<>();
        for (int i = 0;i<tmpPosition.size();i++) {
            tmpQuestion.add(new Question(tmpPosition.get(i).getQuestionId(),tmpPosition.get(i).getQuestionContent()));
        }


        GetRecruitPositionResponseDTO getRecruitPositionResponseDTO
                = new GetRecruitPositionResponseDTO(tmpPosition.get(0).getPositionId(),
                tmpPosition.get(0).getField(),tmpPosition.get(0).getDivision(),
        tmpPosition.get(0).getQuestionId()
                ,tmpQuestion);
//user 좋아요 여부 


        GetRecruitDetailResponseDTO getRecruitDetailResponseDTO
                = new GetRecruitDetailResponseDTO(tmp,getRecruitPositionResponseDTO);


        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setMessage("상세 조회 성공");
        resultResponse.setStatus("200");
        resultResponse.setSuccess("true");
        resultResponse.setData(getRecruitDetailResponseDTO);
        return SimpleResponse.ok(resultResponse);

    }
}
