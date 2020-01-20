package com.rest.recruit.dto.response;

import com.rest.recruit.model.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
