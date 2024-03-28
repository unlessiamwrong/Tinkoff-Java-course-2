package edu.java.commands;

import edu.java.clients.ScrapperClient;
import edu.java.dto.kafka.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class StartCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;
    private final KafkaTemplate<String, Message> messageKafkaTemplate;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Registration";
    }

    public void execute(Long userId) {
        messageKafkaTemplate.send("message", new Message(userId, "start", null));
    }
}
