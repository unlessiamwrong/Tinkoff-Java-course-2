package edu.java.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
    Long id,
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {
}
