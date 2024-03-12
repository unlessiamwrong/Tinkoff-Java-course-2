package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.services.LinkUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
        if(!linkUpdater.update().isEmpty()){
            botClient.getUpdates();
        }
    }
}
