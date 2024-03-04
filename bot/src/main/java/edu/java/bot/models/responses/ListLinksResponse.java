package edu.java.bot.models.responses;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    long size
) {
}
