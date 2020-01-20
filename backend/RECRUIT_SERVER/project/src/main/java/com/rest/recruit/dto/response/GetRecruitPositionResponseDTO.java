package com.rest.recruit.dto.response;

import com.rest.recruit.model.Position;
import com.rest.recruit.model.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetRecruitPositionResponseDTO {

    private int positionId;
    private String field;
    private int division;
    private int questionId;
    private List<Question> employments;

    public GetRecruitPositionResponseDTO(int positionId, String field, int division, int questionId, List<Question> tmpQuestion) {

        this.positionId = positionId;
        this.field = field;
        this.division = division;
        this.questionId = questionId;
        this.employments = tmpQuestion;
    }

    public static GetRecruitPositionResponseDTO of(Position position, List<Question> tmpQuestion) {
        return GetRecruitPositionResponseDTO.builder()
                .positionId(position.getPositionId())
                .field(position.getField())
                .division(position.getDivision())
                .questionId(position.getQuestionId())
                .employments(tmpQuestion).build();


    }
}
