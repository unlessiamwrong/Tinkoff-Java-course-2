package edu.java.commands;

import edu.java.clients.ScrapperClient;
import java.net.URI;
import edu.java.dto.kafka.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class TrackCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;
    private final KafkaTemplate<String, edu.java.dto.kafka.Message> messageKafkaTemplate;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking link";
    }

    public void execute(Long userId, URI link) {
        messageKafkaTemplate.send("message", new Message(userId, "track", link));
    }
}
