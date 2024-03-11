package edu.java.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class StackOfItem {

    @JsonProperty("last_activity_date")
    private int lastActivityDate;

    @JsonProperty("title")
    private String title;

    @JsonProperty("question_id")
    private int questionId;

}
