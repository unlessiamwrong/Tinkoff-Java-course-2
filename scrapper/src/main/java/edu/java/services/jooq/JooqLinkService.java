package edu.java.services.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.services.LinkService;
import edu.java.utilities.Mapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final JooqLinkRepository jooqLinkRepository;

    private final JooqUserRepository jooqUserRepository;

    @Override
    public LinkResponse add(long chatId, URI url) {
        User user = jooqUserRepository.getUserByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        if (!isLinkValid(url)) {
            throw new InvalidParamsException("Link:'" + url + "' is invalid. Use Github's or StackOverflow's links");
        }

        Link link = jooqLinkRepository.getLinkFromUser(user.getId(), url);
        if (link != null) {
            throw new LinkAlreadyExists("Link:'" + url + "' already exists");
        }
        return Mapper.executeForObject(jooqLinkRepository.add(chatId, url));
    }

    @Override
    public LinkResponse remove(long chatId, URI url) {
        User user = jooqUserRepository.getUserByChatId(chatId);

        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        long userId = user.getId();
        Link link = jooqLinkRepository.getLinkFromUser(userId, url);
        if (link == null) {
            throw new NotFoundException("Link:'" + url + "' does not exist");
        }

        jooqLinkRepository.remove(userId, link);
        return Mapper.executeForObject(link);
    }

    @Override
    public ListLinksResponse listAll(long chatId) {
        User user = jooqUserRepository.getUserByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        return Mapper.executeForList(jooqLinkRepository.findAllUserLinks(user.getId()));
    }
}
