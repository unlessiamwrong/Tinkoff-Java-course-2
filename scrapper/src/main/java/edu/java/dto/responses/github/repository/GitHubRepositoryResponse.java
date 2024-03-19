package edu.java.dto.responses.github.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt

) {
}
