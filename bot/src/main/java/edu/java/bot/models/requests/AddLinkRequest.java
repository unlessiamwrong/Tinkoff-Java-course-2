package edu.java.bot.models.requests;

import edu.java.bot.models.responses.LinkResponse;
import java.util.List;

public record AddLinkRequest(
    List<LinkResponse> links,
    long size
) {
}
