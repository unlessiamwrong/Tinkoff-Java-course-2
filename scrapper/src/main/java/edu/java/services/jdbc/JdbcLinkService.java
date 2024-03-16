package edu.java.services.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@SuppressWarnings("MultipleStringLiterals")
@Service
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcUserRepository jdbcUserRepository;

    @Autowired JdbcLinkService(JdbcLinkRepository jdbcLinkRepository, JdbcUserRepository jdbcUserRepository) {
        this.jdbcLinkRepository = jdbcLinkRepository;
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public Link add(long userId, URI url) {
        User user = jdbcUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        if (!isLinkValid(url)) {
            throw new InvalidParamsException("Link:'" + url + "' is invalid. Use Github's or StackOverflow's links");
        }
        Link link = jdbcLinkRepository.getLinkFromUser(userId, url);
        if (link != null) {
            throw new LinkAlreadyExists("Link:'" + url + "' already exists");
        }
        return jdbcLinkRepository.add(userId, url);
    }

    @Override
    public Link remove(long userId, URI url) {
        User user = jdbcUserRepository.getUser(userId);

        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }

        Link link = jdbcLinkRepository.getLinkFromUser(userId, url);
        if (link == null) {
            throw new NotFoundException("Link:'" + url + "' does not exist");
        } else {
            jdbcLinkRepository.remove(userId, link);
        }
        return link;
    }

    @Override
    public List<Link> listAll(long userId) {
        User user = jdbcUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        return jdbcLinkRepository.findAllUserLinks(userId);
    }
}
