package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.model.Message;

public interface CommandManager {

    String commandName();

    void startProcess(Message message);

}
