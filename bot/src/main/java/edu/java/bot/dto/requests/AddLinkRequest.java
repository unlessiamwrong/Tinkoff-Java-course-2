package edu.java.bot.dto.requests;

import edu.java.bot.dto.responses.LinkResponse;
import java.util.List;

public record AddLinkRequest(
    List<LinkResponse> links,
    long size
) {
}
