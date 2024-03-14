package edu.java.dto.responses.github.commits;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubCommit(

    @JsonProperty("author")
    GitHubAuthor gitHubAuthor,

    @JsonProperty("message")
    String message

) {
}
