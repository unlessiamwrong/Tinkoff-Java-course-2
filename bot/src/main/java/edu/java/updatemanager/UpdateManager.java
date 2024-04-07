package edu.java.updatemanager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.dto.requests.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateManager {

    private final TelegramBot bot;

    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        for (Long userId : linkUpdateRequest.userIds()) {
            bot.execute(new SendMessage(userId, linkUpdateRequest.description()));
        }
    }

    public void sendUpdates(List<LinkUpdateRequest> linkUpdateRequests) {
        for (LinkUpdateRequest update : linkUpdateRequests) {
            for (Long userId : update.userIds()) {
                bot.execute(new SendMessage(userId, update.description()));
            }
        }
    }

}
