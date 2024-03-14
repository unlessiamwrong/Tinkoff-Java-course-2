package edu.java.dto.responses.stackoverflow.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionItem;
import java.util.List;

public record StackOfQuestionResponse(
    @JsonProperty("items")
    List<StackOfQuestionItem> items
) {

}
