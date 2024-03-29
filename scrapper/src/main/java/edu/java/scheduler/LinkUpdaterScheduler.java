package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.services.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings("RegexpSinglelineJava")
@RequiredArgsConstructor
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdater<?> linkUpdater;
    private final BotClient botClient;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        botClient.getUpdates(linkUpdater.update());
    }
}
