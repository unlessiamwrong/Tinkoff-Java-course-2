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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JooqLinkUpdater implements LinkUpdater {

    private final JooqLinkRepository jooqLinkRepository;

    private final GetLinkDataItems getLinkDataItems;

    @Override
    public List<LinkUpdateRequest> update() {
        List<Link> notUpdatedLinks = jooqLinkRepository.findAll();
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>();
        if (notUpdatedLinks.isEmpty()) {
            return null;
        }
        for (Link link : notUpdatedLinks) {
            DataSet dataSet = getLinkDataItems.execute(link.getName());
            if (dataSet == null) {
                jooqLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                continue;
            }

            OffsetDateTime currentDateTime = dataSet.dateTime();
            if (currentDateTime.isAfter(link.getLastUpdate())) {
                linkUpdateRequests.add(prepareUpdate(currentDateTime, link, dataSet));
            } else {
                jooqLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
            }
        }
        return linkUpdateRequests;
    }

    @Override
    public LinkUpdateRequest prepareUpdate(OffsetDateTime currentDateTime, Link link, DataSet dataSet) {
        String linkUrl = link.getName();
        String message =
            linkUrl + " is updated with " + dataSet.activityType() + " by " + dataSet.authorName()
                + " at " + currentDateTime;

        return new LinkUpdateRequest(
            link.getId(),
            URI.create(link.getName()),
            message,
            jooqLinkRepository.updateLinkGetRelatedUsers(
                link.getId(),
                currentDateTime,
                OffsetDateTime.now()
            )
        );
    }
}
