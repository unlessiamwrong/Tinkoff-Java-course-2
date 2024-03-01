package edu.java.bot.commands.commandmanagers.untrackcommandmanager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.repositories.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackExecutor {

    private final TelegramBot bot;

    private final UntrackCommand untrackCommand;

    @Autowired
    public UntrackExecutor(TelegramBot bot, UntrackCommand untrackCommand) {
        this.bot = bot;
        this.untrackCommand = untrackCommand;
    }

    public void execute(User currentUser, Long chatId, String linkToUntrack) {
        boolean executionIsSuccessful = untrackCommand.execute(currentUser, linkToUntrack);
        if (executionIsSuccessful) {
            bot.execute(new SendMessage(chatId, "Link untracked successfully"));
        } else {
            bot.execute(new SendMessage(chatId, "There is no such link. Please choose from current /list"));
        }
    }
}
