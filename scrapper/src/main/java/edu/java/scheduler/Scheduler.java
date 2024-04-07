package edu.java.scheduler;

import edu.java.services.LinkUpdater;
import edu.java.updatemanager.UpdateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings("RegexpSinglelineJava")
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final LinkUpdater<?> linkUpdater;
    private final UpdateManager updateManager;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        updateManager.getUpdate(linkUpdater.update());
    }
}
