package com.rest.recruit.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int positionId;
    private String field;
    private int division;
    private int questionId;


    private String questionContent;
    private List<Question> quesitons;

    public void setEmployments(List<Question> tmpQuestion) {
        this.quesitons = tmpQuestion;

    }


    //employMentResume

}
