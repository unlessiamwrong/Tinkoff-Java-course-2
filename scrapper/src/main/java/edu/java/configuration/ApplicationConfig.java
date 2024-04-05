package edu.java.configuration;

import edu.java.configuration.access_db.AccessTypeDb;
import edu.java.configuration.access_updater.AccessTypeUpdater;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    String botBaseUrl,
    String gitHubBaseUrl,
    String stackOfBaseUrl,
    AccessTypeDb databaseAccessType,

    AccessTypeUpdater updaterAccessType,
    int retryMaxAttempts,

    int retryDelay
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

}
