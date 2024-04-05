package edu.java.configuration.access_updater;

import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.updatemanager.KafkaUpdateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "updater-access-type", havingValue = "kafka")
@Configuration
public class KafkaAccessConfiguration {

    private final KafkaTemplate<String, LinkUpdateRequest> updateKafkaTemplate;

    @Bean
    public KafkaUpdateManager kafkaUpdateManager() {
        return new KafkaUpdateManager(updateKafkaTemplate);
    }
}
