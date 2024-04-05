package edu.java.updatemanager;

import edu.java.clients.BotClient;
import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpUpdateManager implements UpdateManager {

    private final BotClient botClient;

    @Override
    public void getUpdate(List<LinkUpdateRequest> linkUpdateRequests) {
        botClient.getUpdates(linkUpdateRequests);
    }
}
