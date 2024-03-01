package edu.java.bot.commands.commandmanagers.untrackcommandmanager;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.commandmanagers.CommandManager;
import edu.java.bot.repositories.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommandManager implements CommandManager {

    private final UserRepository userRepository;

    private final UserNotRegisteredResponse userNotRegisteredResponse;

    private final UntrackRequestAnalyzer untrackRequestAnalyzer;

    @Autowired UntrackCommandManager(
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse,
        UntrackRequestAnalyzer untrackRequestAnalyzer
    ) {
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;
        this.untrackRequestAnalyzer = untrackRequestAnalyzer;

    }

    @Override
    public String commandName() {
        return "/untrack";
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);
        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {
            untrackRequestAnalyzer.execute(currentUser, chatId, message);
        }
    }
}
