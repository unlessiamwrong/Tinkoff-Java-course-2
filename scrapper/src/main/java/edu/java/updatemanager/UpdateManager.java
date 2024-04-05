package edu.java.updatemanager;

import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;

public interface UpdateManager {

    void getUpdate(List<LinkUpdateRequest> linkUpdateRequests);
}
