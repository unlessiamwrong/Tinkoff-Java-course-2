package edu.java.listener;

import edu.java.dto.kafka.Message;
import edu.java.utilities.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final MessageProcessor messageProcessor;

    @KafkaListener(id = "id",
                   topics = "message", containerFactory = "messageKafkaListenerContainerFactory")
    public void listen(Message message) {
        messageProcessor.analyzeAndExecute(message);
    }
}
