package edu.java.services;

import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import java.net.URI;

public interface LinkService {
    LinkResponse add(long userId, URI url);

    LinkResponse remove(long userId, URI url);

    ListLinksResponse listAll(long userId);
}
