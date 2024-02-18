package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;

public interface Command {

    String name();

    String description();

    String startProcess(Message message, TelegramBot bot);

    default BotCommand toApiCommand() {
        return new BotCommand(name(), description());
    }

}
