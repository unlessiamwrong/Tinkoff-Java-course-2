package edu.java.services;

import edu.java.domain.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long userId, URI url);

    Link remove(long userId, URI url);

    List<Link> listAll(long userId);
}
