package edu.java.scheduler;

import edu.java.services.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@SuppressWarnings("RegexpSinglelineJava")
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater<?> linkUpdater;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        linkUpdater.update();
    }
}
