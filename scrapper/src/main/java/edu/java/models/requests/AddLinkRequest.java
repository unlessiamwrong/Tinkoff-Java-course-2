package edu.java.models.requests;

import edu.java.models.responses.LinkResponse;
import java.util.List;

public record AddLinkRequest(
    List<LinkResponse> links,
    long size
) {
}
