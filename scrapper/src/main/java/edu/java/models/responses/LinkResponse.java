package edu.java.models.responses;

import java.net.URI;

public record LinkResponse(
    long id,
    URI url
) {
}
