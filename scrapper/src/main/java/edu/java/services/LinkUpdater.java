package edu.java.services;

import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.utilities.links.DataSet;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkUpdater<T> {
    List<LinkUpdateRequest> update();

    LinkUpdateRequest prepareUpdate(OffsetDateTime currentDateTime, T entity, DataSet dataSet);

}
