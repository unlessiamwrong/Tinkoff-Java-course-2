package edu.java.services.jdbc;

import edu.java.domain.Link;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.repositories.LinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.utilities.GetLinkData;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkRepository linkRepository;

    private final GetLinkData getLinkData;

    @Autowired JdbcLinkUpdater(LinkRepository linkRepository, GetLinkData getLinkData) {

        this.linkRepository = linkRepository;
        this.getLinkData = getLinkData;

    }

    @Override
    public List<LinkUpdateRequest> update() {
        List<Link> notUpdatedLinks = linkRepository.findAll();
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>();
        if (!notUpdatedLinks.isEmpty()) {
            for (Link link : notUpdatedLinks) {
                OffsetDateTime linkLastUpdate = getLinkData.execute(link.getName());
                linkUpdateRequests.add(new LinkUpdateRequest(
                    link.getId(),
                    URI.create(link.getName()),
                    "link is updated",
                    linkRepository.updateLinkGetRelatedUsers(link.getId(), linkLastUpdate, OffsetDateTime.now())
                ))
                ;
            }
        }
        return linkUpdateRequests;
    }
}
