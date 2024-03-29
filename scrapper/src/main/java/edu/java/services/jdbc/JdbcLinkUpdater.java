package edu.java.services.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater<Link> {

    private final JdbcLinkRepository jdbcLinkRepository;

    private final GetLinkDataItems getLinkDataItems;

    @Override
    public List<LinkUpdateRequest> update() {
        List<Link> notUpdatedLinks = jdbcLinkRepository.findAll();
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>(notUpdatedLinks.size());
        if (notUpdatedLinks.isEmpty()) {
            return linkUpdateRequests;
        }
        for (Link link : notUpdatedLinks) {
            DataSet dataSet = getLinkDataItems.execute(link.getName());
            if (dataSet == null) {
                jdbcLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                continue;
            }

            OffsetDateTime currentDateTime = dataSet.dateTime();
            if (currentDateTime.isAfter(link.getLastUpdate())) {
                linkUpdateRequests.add(prepareUpdate(currentDateTime, link, dataSet));
            } else {
                jdbcLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
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
            jdbcLinkRepository.updateLinkGetRelatedUsers(
                link.getId(),
                currentDateTime,
                OffsetDateTime.now()
            )
        );
    }
}
