package edu.java.bot.commands;

import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.utilities.others.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Order(1)
public class StartCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Registration";
    }

    public String execute(Long userId) {
        try {
            scrapperClient.postUser(userId);
        } catch (WebClientResponseException e) {
            return Mapper.getExceptionMessage(e.getResponseBodyAsString());
        }
        return "You are successfully registered";
    }
}
