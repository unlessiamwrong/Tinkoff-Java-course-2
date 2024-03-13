package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.domain.Link;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.services.LinkUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@SuppressWarnings("RegexpSinglelineJava")
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    private final BotClient botClient;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdater linkUpdater, BotClient botClient) {
        this.linkUpdater = linkUpdater;
        this.botClient = botClient;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        List<LinkUpdateRequest> linkUpdateRequests = linkUpdater.update();
        for(LinkUpdateRequest linkUpdateRequest: linkUpdateRequests){
//            botClient.getUpdates(linkUpdateRequest);
            System.out.println(linkUpdateRequest.id());
            System.out.println(linkUpdateRequest.description());
            System.out.println(linkUpdateRequest.url());
            System.out.println(linkUpdateRequest.userIds());
        }
    }
}
