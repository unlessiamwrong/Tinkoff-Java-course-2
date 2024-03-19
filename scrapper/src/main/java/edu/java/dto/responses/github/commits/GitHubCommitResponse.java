package edu.java.dto.responses.github.commits;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubCommitResponse(
    @JsonProperty("commit")
    GitHubCommit gitHubCommit
) {
}
