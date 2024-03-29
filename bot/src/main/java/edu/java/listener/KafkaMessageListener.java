package edu.java.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.dto.kafka.Response;
import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final TelegramBot bot;

    @KafkaListener(id = "id",
                   topics = "response", containerFactory = "responseKafkaListenerContainerFactory")
    public void getResponse(Response response) {
        bot.execute(new SendMessage(response.userId(), response.message()));
    }

    @KafkaListener(id = "id1",
                   topics = "update", containerFactory = "updateKafkaListenerContainerFactory")
    public void getUpdate(List<LinkUpdateRequest> updates) {
        for (LinkUpdateRequest linkUpdateRequest : updates) {
            for (Long userId : linkUpdateRequest.userIds()) {
                bot.execute(new SendMessage(userId, linkUpdateRequest.description()));
            }
        }
    }

}
