package edu.java.dto.responses.stackoverflow.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOfQuestionItem(

    @JsonProperty("last_activity_date")
    long lastActivityDate,

    @JsonProperty("title")
    String title,

    @JsonProperty("question_id")
    long questionId
) {
}
