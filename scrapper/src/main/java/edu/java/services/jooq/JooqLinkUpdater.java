package edu.java.services.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JooqLinkUpdater implements LinkUpdater {

    private final JooqLinkRepository jooqLinkRepository;

    private final GetLinkDataItems getLinkDataItems;

    @Autowired JooqLinkUpdater(JooqLinkRepository jooqLinkRepository, GetLinkDataItems getLinkDataItems) {
        this.jooqLinkRepository = jooqLinkRepository;
        this.getLinkDataItems = getLinkDataItems;

    }

    @Override
    public List<LinkUpdateRequest> update() {

        List<Link> notUpdatedLinks = jooqLinkRepository.findAll();
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>();
        if (!notUpdatedLinks.isEmpty()) {
            for (Link link : notUpdatedLinks) {
                String linkUrl = link.getName();
                DataSet dataSet = getLinkDataItems.execute(linkUrl);
                if (dataSet == null) {
                    jooqLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                    continue;
                }
                OffsetDateTime currentDateTime = dataSet.dateTime();
                if (currentDateTime.isAfter(link.getLastUpdate())) {
                    String message =
                        linkUrl + " is updated with " + dataSet.activityType() + " by " + dataSet.authorName()
                            + " at " + currentDateTime;

                    linkUpdateRequests.add(new LinkUpdateRequest(
                        link.getId(),
                        URI.create(link.getName()),
                        message,
                        jooqLinkRepository.updateLinkGetRelatedUsers(
                            link.getId(),
                            currentDateTime,
                            OffsetDateTime.now()
                        )
                    ));
                } else {
                    jooqLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                }
            }
        }
        return linkUpdateRequests;
    }
}
