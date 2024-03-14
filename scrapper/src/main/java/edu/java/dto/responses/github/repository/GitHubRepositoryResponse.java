package edu.java.dto.responses.github.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.time.OffsetDateTime;
import java.util.List;

public record GitHubRepositoryResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt

) {
}
