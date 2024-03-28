package edu.java.dto.responses;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    long size
) {
}
