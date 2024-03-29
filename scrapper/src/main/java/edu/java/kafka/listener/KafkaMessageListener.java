package edu.java.kafka.listener;

import edu.java.dto.kafka.Message;
import edu.java.kafka.commands.MessageProcessor;
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
        messageProcessor.analyzeAndTransfer(message);
    }
}
