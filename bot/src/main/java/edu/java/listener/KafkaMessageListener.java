package edu.java.listener;

import edu.java.dto.requests.LinkUpdateRequest;
import edu.java.updatemanager.UpdateManager;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final static Logger LOGGER = LogManager.getLogger();

    private final UpdateManager updateManager;

    private final KafkaTemplate<String, String> dlqKafkaTemplate;

    @KafkaListener(id = "2",
                   topics = "update", containerFactory = "updateKafkaListenerContainerFactory")
    public void getUpdate(LinkUpdateRequest linkUpdateRequest) {
        updateManager.sendUpdate(linkUpdateRequest);
    }

    @DltHandler
    public void getUpdateDlq(LinkUpdateRequest linkUpdateRequest, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        LOGGER.info("Event on dlt topic={}, payload={}", topic, linkUpdateRequest);
        dlqKafkaTemplate.send("update_dlq", topic + "moved to DLQ");
    }
}

