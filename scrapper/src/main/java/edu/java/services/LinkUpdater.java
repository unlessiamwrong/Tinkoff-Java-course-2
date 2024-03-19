package edu.java.services;

import edu.java.domain.jdbc.Link;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.utilities.links.DataSet;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkUpdater {
    List<LinkUpdateRequest> update();

    LinkUpdateRequest prepareUpdate(OffsetDateTime currentDateTime, Link link, DataSet dataSet);

}
