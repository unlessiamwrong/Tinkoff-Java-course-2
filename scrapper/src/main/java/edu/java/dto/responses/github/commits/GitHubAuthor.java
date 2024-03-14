package edu.java.dto.responses.github.commits;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubAuthor(

    @JsonProperty("date")
    OffsetDateTime date,

    @JsonProperty("name")
    String name

) {
}
