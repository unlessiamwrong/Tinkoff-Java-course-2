package edu.java.utilities.links;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record DataSet(
    OffsetDateTime dateTime,
    String authorName,
    String activityType
) {

}
