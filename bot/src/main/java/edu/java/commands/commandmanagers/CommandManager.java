package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.model.Message;

public interface CommandManager {

    String commandName();

    void startProcess(Message message);

}
