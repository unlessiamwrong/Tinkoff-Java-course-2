package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommandManager implements CommandManager {

    private static final String COMMAND_NAME = "/track";
    private final TelegramBot bot;
    private final UserRepository userRepository;
    private final UserNotRegisteredResponse userNotRegisteredResponse;

    @Autowired TrackCommandManager(
        TelegramBot bot,
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse
    ) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;

    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);

        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {
            if (message.text().equals(COMMAND_NAME)) {
                bot.execute(new SendMessage(chatId, "Please enter your link").replyMarkup(new ForceReply()));
            } else {
                currentUser.addLink(new Link(message.text(), "InfoStub"));
                bot.execute(new SendMessage(chatId, "Link added successfully"));
            }
        }
    }
}
