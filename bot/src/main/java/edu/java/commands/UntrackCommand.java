package edu.java.commands;

import edu.java.clients.ScrapperClient;
import edu.java.dto.kafka.Message;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(4)
public class UntrackCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;
    private final KafkaTemplate<String, Message> messageKafkaTemplate;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking link";
    }

    public void execute(Long userId, URI link) {
        messageKafkaTemplate.send("message", new Message(userId, "untrack", link));
    }

}
