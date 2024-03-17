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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JdbcLinkUpdater implements LinkUpdater {

    private final JdbcLinkRepository jdbcLinkRepository;

    private final GetLinkDataItems getLinkDataItems;

    @Override
    public List<LinkUpdateRequest> update() {

        List<Link> notUpdatedLinks = jdbcLinkRepository.findAll();
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>();
        if (!notUpdatedLinks.isEmpty()) {
            for (Link link : notUpdatedLinks) {
                String linkUrl = link.getName();
                DataSet dataSet = getLinkDataItems.execute(linkUrl);
                if (dataSet == null) {
                    jdbcLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
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
                        jdbcLinkRepository.updateLinkGetRelatedUsers(
                            link.getId(),
                            currentDateTime,
                            OffsetDateTime.now()
                        )
                    ));
                } else {
                    jdbcLinkRepository.updateLinkWithLastCheckForUpdate(link.getId(), OffsetDateTime.now());
                }
            }
        }
        return linkUpdateRequests;
    }
}
