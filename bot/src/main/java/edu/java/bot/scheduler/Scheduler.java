package edu.java.bot.scheduler;

import java.time.Duration;

public record Scheduler(
    Duration interval
) {
}
