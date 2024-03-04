package edu.java.models.responses;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    long size
) {
}
