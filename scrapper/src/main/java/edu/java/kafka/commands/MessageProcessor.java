package edu.java.kafka.commands;

import edu.java.dto.kafka.Message;
import edu.java.dto.kafka.Response;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final StartCommand startCommand;
    private final TrackCommand trackCommand;
    private final UntrackCommand untrackCommand;
    private final ListCommand listCommand;
    private final KafkaTemplate<String, Response> responseKafkaTemplate;

    public void analyzeAndTransfer(Message message) {
        Long userId = message.id();
        String command = message.command();
        URI link = message.link();

        String response = null;
        switch (command) {
            case "start" -> {
                response = startCommand.execute(userId);
            }
            case "track" -> {
                response = trackCommand.execute(userId, link);
            }
            case "untrack" -> {
                response = untrackCommand.execute(userId, link);
            }
            case "list" -> {
                response = listCommand.execute(userId);
            }
        }
        responseKafkaTemplate.send("response", new Response(userId, response));

    }
}
