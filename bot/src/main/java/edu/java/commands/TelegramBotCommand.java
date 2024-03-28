package edu.java.commands;

import com.pengrad.telegrambot.model.BotCommand;

public interface TelegramBotCommand {

    String name();

    String description();

    default BotCommand toApiCommand() {
        return new BotCommand(name(), description());
    }
}
