package edu.java.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

@Getter
public class StackOfQuestionResponse {

    @JsonProperty("items")
    public List<StackOfItem> items;

}
