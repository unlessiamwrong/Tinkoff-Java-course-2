package edu.java.bot.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
    }
}
