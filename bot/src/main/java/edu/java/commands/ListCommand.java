package edu.java.commands;

import edu.java.clients.ScrapperClient;
import edu.java.dto.kafka.Message;
import edu.java.dto.responses.LinkResponse;
import edu.java.utilities.others.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Order(5)
public class ListCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;
    private final KafkaTemplate<String, Message> messageKafkaTemplate;

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "Show all tracked links";
    }

    public void execute(Long userId) {
        messageKafkaTemplate.send("message", new Message(userId, "list", null));
    }
}
