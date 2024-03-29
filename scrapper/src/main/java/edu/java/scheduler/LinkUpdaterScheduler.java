package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.services.LinkUpdater;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SuppressWarnings("RegexpSinglelineJava")
@RequiredArgsConstructor
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdater<?> linkUpdater;
    private final KafkaTemplate<String, List<LinkUpdateRequest>> updateKafkaTemplate;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        List<LinkUpdateRequest> updates = linkUpdater.update();
        updateKafkaTemplate.send("update", updates);
    }
}
