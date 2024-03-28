package edu.java.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.dto.kafka.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final TelegramBot bot;

    @KafkaListener(id = "id",
                   topics = "response", containerFactory = "responseKafkaListenerContainerFactory")
    public void listen(Response response) {
        bot.execute(new SendMessage(response.userId(), response.message()));
    }
}
