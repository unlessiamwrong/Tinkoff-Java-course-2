package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.clients.ScrapperClient;
import edu.java.dto.requests.RemoveLinkRequest;
import edu.java.utilities.others.Mapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Order(4)
public class UntrackCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking link";
    }

    public String execute(Message message) {
        try {
            scrapperClient.deleteLink(message.chat().id(), new RemoveLinkRequest(URI.create(message.text())));
            return "Link untracked successfully";
        } catch (WebClientResponseException e) {
            return Mapper.getExceptionMessage(e.getResponseBodyAsString());
        }
    }

}
