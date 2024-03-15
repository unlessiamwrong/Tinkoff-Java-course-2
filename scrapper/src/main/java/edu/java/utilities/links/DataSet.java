package edu.java.utilities.links;

import java.time.OffsetDateTime;

public record DataSet(
    OffsetDateTime dateTime,
    String authorName,
    String activityType
) {

}
