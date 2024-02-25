package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOverflowClient;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import edu.java.bot.utilities.parser.GitHubLinkParser;
import edu.java.bot.utilities.parser.LinkDetector;
import edu.java.bot.utilities.parser.StackOfLinkParser;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommandManager implements CommandManager {

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
        return "/track";
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);

        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {
            if (message.text().equals("/track")) {
                bot.execute(new SendMessage(chatId, "Please enter your link").replyMarkup(new ForceReply()));
            } else {
                currentUser.addLink(new Link(message.text(), "InfoStub"));
                bot.execute(new SendMessage(chatId, "Link added successfully"));
            }
        }
    }
}
