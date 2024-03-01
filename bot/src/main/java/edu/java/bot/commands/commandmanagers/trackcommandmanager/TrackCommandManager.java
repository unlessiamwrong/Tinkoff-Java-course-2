package edu.java.bot.commands.commandmanagers.trackcommandmanager;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.commandmanagers.CommandManager;
import edu.java.bot.repositories.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("MissingSwitchDefault")
public class TrackCommandManager implements CommandManager {

    private final UserRepository userRepository;
    private final UserNotRegisteredResponse userNotRegisteredResponse;

    private final TrackRequestAnalyzer trackRequestAnalyzer;

    @Autowired TrackCommandManager(
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse,
        TrackRequestAnalyzer trackRequestAnalyzer
    ) {
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;
        this.trackRequestAnalyzer = trackRequestAnalyzer;
    }

    @Override
    public String commandName() {
        return "/track";
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);

        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {
            trackRequestAnalyzer.execute(currentUser, chatId, message);
        }
    }
}
