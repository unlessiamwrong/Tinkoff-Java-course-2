package edu.java.dto.responses.stackoverflow.answers;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOfAnswersOwner(
    @JsonProperty("display_name")
    String name
) {
}
