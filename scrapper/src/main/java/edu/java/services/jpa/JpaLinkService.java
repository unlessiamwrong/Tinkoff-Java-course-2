package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.services.LinkService;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Service
public class JpaLinkService implements LinkService  {

    private final JpaUserRepository jpaUserRepository;
    private final JpaLinkRepository jpaLinkRepository;
    private final GetLinkDataItems getLinkDataItems;

    private final GetLinkDataRepository getLinkDataRepository;

    @Override
    public LinkResponse add(long userId, URI url) {
        User user = jpaUserRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        String urlString = url.toString();
        OffsetDateTime lastUpdateAt = getLinkLastUpdate(urlString);
        Link link = new Link();
        link.setLastUpdate(lastUpdateAt);
        link.setName(urlString);
        jpaLinkRepository.saveAndFlush(link);





        return new LinkResponse();
    }

    @Override
    public LinkResponse remove(long userId, URI url) {
        return null;
    }

    @Override
    public ListLinksResponse listAll(long userId) {
        return null;
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
