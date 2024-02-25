package edu.java.bot.commands;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class HelpCommand implements TelegramBotCommand {

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "Show all available commands";
    }

}
