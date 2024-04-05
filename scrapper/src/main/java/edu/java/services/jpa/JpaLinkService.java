package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.LinkAlreadyExists;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.services.LinkService;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class JpaLinkService implements LinkService {

    private final JpaUserRepository jpaUserRepository;
    private final JpaLinkRepository jpaLinkRepository;
    private final GetLinkDataItems getLinkDataItems;

    private final GetLinkDataRepository getLinkDataRepository;

    @Override
    public LinkResponse add(long chatId, URI url) {
        User user = jpaUserRepository.findByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        if (!isLinkValid(url)) {
            throw new InvalidParamsException("Link:'" + url + "' is invalid. Use Github's or StackOverflow's links");
        }

        Long userId = user.getId();
        String urlString = url.toString();
        Link link = new Link();
        link.setLastUpdate(getLinkLastUpdate(urlString));
        link.setName(urlString);

        List<Link> allUserLinks = jpaUserRepository.findAllUserLinksByUserId(userId);
        for (Link linkToCheck : allUserLinks) {
            if (linkToCheck.getName().equals(urlString)) {
                throw new LinkAlreadyExists("Link:'" + url + "' already exists");
            }
        }

        Long linkId;
        Link possibleLinkFromLinks = jpaLinkRepository.findByNameLike(urlString);
        if (possibleLinkFromLinks == null) {
            jpaLinkRepository.save(link);
            linkId = link.getId();
        } else {
            linkId = possibleLinkFromLinks.getId();
        }

        jpaUserRepository.addUserLink(userId, linkId);

        return new LinkResponse(linkId, url);
    }

    @Override
    public LinkResponse remove(long chatId, URI url) {
        User user = jpaUserRepository.findByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        Long userId = user.getId();
        String urlString = url.toString();
        Link linkFromLinks = jpaLinkRepository.findByNameLike(urlString);

        if (linkFromLinks == null) {
            throw new NotFoundException("Link:'" + url + "' does not exist");
        }

        Link possibleLinkFromUserLinks = jpaUserRepository.findUserLinkByUserIdAndLinkId(userId, linkFromLinks.getId());
        if (possibleLinkFromUserLinks == null) {
            throw new NotFoundException("Link:'" + url + "' does not exist");
        }

        jpaUserRepository.removeUserLink(userId, linkFromLinks.getId());
        if (jpaLinkRepository.findUsersByLinkId(linkFromLinks.getId()).isEmpty()) {
            jpaLinkRepository.delete(linkFromLinks);
        }

        return new LinkResponse(linkFromLinks.getId(), url);

    }

    @Override
    public ListLinksResponse listAll(long chatId) {
        User user = jpaUserRepository.findByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        Long userId = user.getId();
        List<Link> allUserLinks = jpaUserRepository.findAllUserLinksByUserId(userId);
        List<LinkResponse> allUserLinksDTO = new ArrayList<>(allUserLinks.size());
        for (Link link : allUserLinks) {
            allUserLinksDTO.add(new LinkResponse(link.getId(), URI.create(link.getName())));
        }

        return new ListLinksResponse(allUserLinksDTO, allUserLinksDTO.size());
    }

    private OffsetDateTime getLinkLastUpdate(String urlString) {
        OffsetDateTime lastUpdateAt;
        DataSet dataSet = getLinkDataItems.execute(urlString);
        if (dataSet != null) {
            lastUpdateAt = dataSet.dateTime();
        } else {
            lastUpdateAt = getLinkDataRepository.execute(urlString);
        }

        return lastUpdateAt;
    }
}
