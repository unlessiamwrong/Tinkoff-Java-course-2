package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("/help")
@Order(2)
public class HelpCommand implements Command {

    @Override
    public String startProcess(Message message, TelegramBot bot) {
        return "";
    }

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "Show all available commands";
    }

}
