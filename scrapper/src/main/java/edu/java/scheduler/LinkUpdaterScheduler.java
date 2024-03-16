package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.services.LinkUpdater;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings("RegexpSinglelineJava")
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    private final BotClient botClient;

    @Autowired
    public LinkUpdaterScheduler(@Qualifier("jooqLinkUpdater") LinkUpdater linkUpdater, BotClient botClient) {
        this.linkUpdater = linkUpdater;
        this.botClient = botClient;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        linkUpdater.update();
    }
}
