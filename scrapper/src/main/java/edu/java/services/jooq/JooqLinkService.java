package edu.java.services.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
@Service
public class JooqLinkService implements LinkService {

    private final JooqUserRepository jooqUserRepository;

    private final JooqLinkRepository jooqLinkRepository;

    @Override
    public Link add(long userId, URI url) {
        User user = jooqUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        if (!isLinkValid(url)) {
            throw new InvalidParamsException("Link:'" + url + "' is invalid. Use Github's or StackOverflow's links");
        }
        Link link = jooqLinkRepository.getLinkFromUser(userId, url);
        if (link != null) {
            throw new LinkAlreadyExists("Link:'" + url + "' already exists");
        }
        return jooqLinkRepository.add(userId, url);
    }

    @Override
    public Link remove(long userId, URI url) {
        User user = jooqUserRepository.getUser(userId);

        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        Link link = jooqLinkRepository.getLinkFromUser(userId, url);
        if (link == null) {
            throw new NotFoundException("Link:'" + url + "' does not exist");
        } else {
            jooqLinkRepository.remove(userId, link);
        }
        return link;
    }

    @Override
    public List<Link> listAll(long userId) {
        User user = jooqUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        return jooqLinkRepository.findAllUserLinks(userId);
    }
}
