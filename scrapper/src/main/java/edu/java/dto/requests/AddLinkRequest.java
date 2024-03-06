package edu.java.dto.requests;

import edu.java.dto.responses.LinkResponse;
import java.util.List;

public record AddLinkRequest(
    List<LinkResponse> links,
    long size
) {
}
