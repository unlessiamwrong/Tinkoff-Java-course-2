package edu.java.dto.responses.stackoverflow.answers;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOfAnswersItem(
    @JsonProperty("owner")
    StackOfAnswersOwner owner,

    @JsonProperty("creation_date")
    long creationDate
) {
}
