package edu.java.bot.models.responses;

import java.net.URI;

public record LinkResponse(
    long id,
    URI url
) {
}
