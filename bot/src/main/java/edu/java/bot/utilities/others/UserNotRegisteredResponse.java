package edu.java.bot.utilities.others;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UserNotRegisteredResponse {
    private final TelegramBot bot;

    private UserNotRegisteredResponse(TelegramBot bot) {
        this.bot = bot;
    }

    public void send(Long chatId) {
        bot.execute(new SendMessage(chatId, "You are not registered. Please use command /start"));
    }
}
