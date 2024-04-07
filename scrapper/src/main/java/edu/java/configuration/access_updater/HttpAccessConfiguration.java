package edu.java.configuration.access_updater;

import edu.java.clients.BotClient;
import edu.java.updatemanager.HttpUpdateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "updater-access-type", havingValue = "http")
@Configuration
public class HttpAccessConfiguration {

    private final BotClient botClient;

    @Bean
    public HttpUpdateManager httpUpdateManager() {
        return new HttpUpdateManager(botClient);
    }

}
