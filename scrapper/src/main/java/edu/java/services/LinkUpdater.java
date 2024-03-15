package edu.java.services;

import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;

public interface LinkUpdater {
    List<LinkUpdateRequest> update();

}
