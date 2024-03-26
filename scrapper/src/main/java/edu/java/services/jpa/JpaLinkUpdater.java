package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater<Link> {

    private static final int INTERVAL_FOR_CHECK_IN_MINUTES = 1;

    private final JpaLinkRepository jpaLinkRepository;

    private final GetLinkDataItems getLinkDataItems;

    @Override
    public List<LinkUpdateRequest> update() {
        List<Link> notUpdatedLinks =
            jpaLinkRepository.findLinksByLastCheckForUpdateIsNullOrLessThanDate(OffsetDateTime.now()
                .minusMinutes(INTERVAL_FOR_CHECK_IN_MINUTES));
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>(notUpdatedLinks.size());
        if (notUpdatedLinks.isEmpty()) {
            return linkUpdateRequests;
        }
        for (Link link : notUpdatedLinks) {
            DataSet dataSet = getLinkDataItems.execute(link.getName());
            if (dataSet == null) {
                jpaLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                continue;
            }

            OffsetDateTime currentDateTime = dataSet.dateTime();
            if (currentDateTime.isAfter(link.getLastUpdate())) {
                linkUpdateRequests.add(prepareUpdate(currentDateTime, link, dataSet));
            } else {
                jpaLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
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

        jpaLinkRepository.updateLinkWithLastUpdateAndLastCheckForUpdate(
            link.getId(),
            currentDateTime,
            OffsetDateTime.now()
        );

        return new LinkUpdateRequest(
            link.getId(),
            URI.create(link.getName()),
            message,
            jpaLinkRepository.findUsersByLinkId(link.getId())
                .stream()
                .map(User::getId)
                .toList()

        );
    }
}
