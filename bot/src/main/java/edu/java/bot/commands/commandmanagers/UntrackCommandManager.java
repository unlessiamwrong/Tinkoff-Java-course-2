package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommandManager implements CommandManager {

    private static final String COMMAND_NAME = "/untrack";
    private final TelegramBot bot;
    private final UserRepository userRepository;

    private final UserNotRegisteredResponse userNotRegisteredResponse;
    private final UntrackCommand untrackCommand;

    @Autowired UntrackCommandManager(
        TelegramBot bot,
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse,
        UntrackCommand untrackCommand
    ) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;
        this.untrackCommand = untrackCommand;

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
            analyzeRequest(currentUser, chatId, message);
        }
    }

    private void analyzeRequest(User currentUser, Long chatId, Message message) {
        List<Link> currentLinks = currentUser.links;
        if (message.text().equals(COMMAND_NAME)) {
            reply(chatId, currentLinks);
        } else {
            untrack(currentUser, chatId, currentLinks, message.text());
        }
    }

    private void reply(Long chatId, List<Link> currentLinks) {
        if (!currentLinks.isEmpty()) {
            String responseText = "Please choose link to untrack \n \n" + "Your current tracked links: \n";
            int orderCount = 1;
            for (Link link : currentLinks) {
                responseText += orderCount + ". " + link.url + "\n";
                orderCount++;
            }
            bot.execute(new SendMessage(chatId, responseText).replyMarkup(new ForceReply()));

        } else {
            bot.execute(new SendMessage(
                chatId,
                "There are no links that are tracked. You can add links with command /track"
            ));
        }
    }

    private void untrack(User currentUser, Long chatId, List<Link> currentLinks, String linkToUntrack) {
        boolean executionIsSuccessful = untrackCommand.execute(currentUser, linkToUntrack);
        if (executionIsSuccessful) {
            bot.execute(new SendMessage(chatId, "Link untracked successfully"));
        } else {
            bot.execute(new SendMessage(chatId, "There is no such link. Please choose from current /list"));
        }
    }
}
