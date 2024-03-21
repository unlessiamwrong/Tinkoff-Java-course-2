package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.dto.requests.AddLinkRequest;
import edu.java.bot.utilities.others.Mapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Order(3)
public class TrackCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking link";
    }

    public String execute(Message message) {
        try {
            scrapperClient.postLink(message.chat().id(), new AddLinkRequest(URI.create(message.text())));
            return "Link added successfully";
        } catch (WebClientResponseException e) {
            return Mapper.getExceptionMessage(e.getResponseBodyAsString());
        }
    }
}
