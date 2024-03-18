package edu.java.services.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.services.LinkService;
import edu.java.utilities.Mapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcUserRepository jdbcUserRepository;

    @Override
    public LinkResponse add(long userId, URI url) {
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
        return Mapper.executeForObject(jdbcLinkRepository.add(userId, url));
    }

    @Override
    public LinkResponse remove(long userId, URI url) {
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
        return Mapper.executeForObject(link);
    }

    @Override
    public ListLinksResponse listAll(long userId) {
        User user = jdbcUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        return Mapper.executeForList(jdbcLinkRepository.findAllUserLinks(userId));
    }
}
