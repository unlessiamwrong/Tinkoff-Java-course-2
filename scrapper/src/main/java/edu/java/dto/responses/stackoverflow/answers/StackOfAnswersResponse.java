package edu.java.dto.responses.stackoverflow.answers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOfAnswersResponse(
    @JsonProperty("items")
    List<StackOfAnswersItem> items
) {
}
