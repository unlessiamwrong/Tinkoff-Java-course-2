package edu.java.updatemanager;

import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaUpdateManager implements UpdateManager {

    private final KafkaTemplate<String, LinkUpdateRequest> updateKafkaTemplate;

    @Override
    public void getUpdate(List<LinkUpdateRequest> linkUpdateRequests) {
        for (LinkUpdateRequest linkUpdateRequest : linkUpdateRequests) {
            updateKafkaTemplate.send("update", linkUpdateRequest);
        }
    }
}
