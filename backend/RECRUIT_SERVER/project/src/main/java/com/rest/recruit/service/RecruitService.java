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
import com.rest.recruit.util.JwtUtil;
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

        if (tmp.size() == 0 ||tmp.isEmpty()) {
            throw new GetCalendarException(); }

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
        /*
        *
        * 프린세스불암산 <meme91322367@gmail.com>

오후 1:51 (11분 전)

나에게
"accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsInVzZXJJZCI6MSwiZW1haWwiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsIm5pY2tuYW1lIjoidGVzdCIsInJvbGVzIjpbIlVTRVIiXSwidG9rZW5UeXBlIjoiQUNDRVNTX1RPS0VOIiwiZXhwIjoxNTgwMTg4ODMxfQ.euTJS0o9_uI6-Q5oLbZsv9e1xJ6SOo_7lfKZtz5L_IU",
        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsInVzZXJJZCI6MSwiZW1haWwiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsIm5pY2tuYW1lIjoidGVzdCIsInJvbGVzIjpbIlVTRVIiXSwidG9rZW5UeXBlIjoiUkVGUkVTSF9UT0tFTiIsImV4cCI6MTU4MTM5NjYzMX0.l5whELZ4WcE3tAqPPLoQAJfzr4HmdRj6d0bUOUqTkNw",
        *
        * */

        /*    <select id="getSimpleRecruitById" resultType="com.rest.recruit.model.SimpleRecruit">
        SELECT
        recruit.id AS recruitId,
        recruit.company_id AS companyId,
        DATE_FORMAT(recruit.end_time,'%Y-%m-%d-%h-%i') AS endTime,
        company.name AS companyName
        FROM recruit
        INNER JOIN company ON company.id = recruit.company_id
        WHERE recruit.id = #{recruitIdx};
    </select>*/
            SimpleRecruit tmp = recruitMapper.getSimpleRecruitById(dataWithToken.getRecruitIdx());

            String tmpString = tmp.getEndTime()+":"+tmp.getRecruitId() + ":" +
                    tmp.getCompanyId() + ":" + tmp.getCompanyName();

            ZSetOperations<String, String> zsetOperations = redisTemplate.opsForZSet();

            RecruitDetail tmpdetail = recruitMapper.GetDetailRecruitPage(dataWithToken.getRecruitIdx());
/*
<sql id = "detail">
        recruit.id AS 'recruit_id',
        company.name AS 'company_name',
        company.logo_url AS 'image_file_name',
        recruit.employment_page_url,
        DATE_FORMAT(recruit.start_time,'%Y-%m-%d %h:%i') AS 'start_time',
        DATE_FORMAT(recruit.end_time,'%Y-%m-%d %h:%i') AS 'end_time',
        recruit.content,
        recruit.recruit_type AS 'recruit_type',
        recruit.view_count,
        COUNT(recruit_like.user_id) AS 'favorite_count'
    </sql>

<select id = "GetDetailRecruitPage" resultType="com.rest.recruit.model.RecruitDetail">
        SELECT
        <include refid = "detail"/>
        FROM recruit
        INNER JOIN company ON company.id = recruit.company_id
        INNER JOIN recruit_like ON recruit_like.recruit_id = recruit.id
        WHERE recruit.id = #{recruitIdx};
    </select>
*/

            List<Position> tmpPosition = recruitMapper.getPosition(dataWithToken.getRecruitIdx());
    /*    <select id = "getPosition" resultType="com.rest.recruit.model.Position">
        SELECT
        position.id AS 'position_id', position.field , position.division,
        question.id AS 'question_id',question.question_content,
        question.question_limit
        FROM recruit
        INNER JOIN position ON position.recruit_id = recruit.id
        INNER JOIN question ON question.position_id = position.id
        WHERE recruit.id = #{recruitIdx};
    </select>*/

            List<Question> tmpQuestion = new ArrayList<>();

            if(zsetOperations.reverseRank("ranking-visit",tmpString) != null){
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

            for (int i = 0;i<tmpPosition.size();i++) {
                tmpQuestion.add(new Question(tmpPosition.get(i).getQuestionId(),
                        tmpPosition.get(i).getQuestionContent(),tmpPosition.get(i).getQuestionLimit()));
            }

            GetRecruitDetailResponseDTO getRecruitDetailResponseDTO
                    = new GetRecruitDetailResponseDTO(tmpdetail,GetRecruitPositionResponseDTO.of(tmpPosition.get(0),tmpQuestion));

            getRecruitDetailResponseDTO.setFavorite(false);

            if (getRecruitDetailResponseDTO == null ) { throw new GetDetailRecruitPageException(); }

        if(dataWithToken.getToken() == null || dataWithToken.getToken().isEmpty()) {

            return SimpleResponse.ok(ResultResponse.builder()
                    .message("상세 조회 성공")
                    .status("200")
                    .success("true")
                    .data(getRecruitDetailResponseDTO).build());


        }


        JwtUtil jwtUtil = new JwtUtil();

        int userIdx = jwtUtil.getAuthentication(dataWithToken.getToken());

        //SELECT * FROM recruit_like
        //        WHERE user_id = #{userIdx} AND recruit_id = #{recruitIdx};
        if (recruitMapper.GetFavorite(userIdx,dataWithToken.getRecruitIdx()) > 0) {
            getRecruitDetailResponseDTO.setFavorite(true);
        }

        return SimpleResponse.ok(ResultResponse.builder()
                .message("상세 조회 성공")
                .status("200")
                .success("true")
                .data(getRecruitDetailResponseDTO).build());
    }

}
