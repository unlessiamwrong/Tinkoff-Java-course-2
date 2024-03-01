package edu.java.bot.commands.commandmanagers.trackcommandmanager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackRequestAnalyzer {

    private final TelegramBot bot;
    private final TrackExecutor trackExecutor;

    @Autowired
    public TrackRequestAnalyzer(TelegramBot bot, TrackExecutor trackExecutor) {
        this.bot = bot;
        this.trackExecutor = trackExecutor;
    }

    public void execute(User currentUser, Long chatId, Message message) {
        if (message.text().equals("/track")) {
            bot.execute(new SendMessage(chatId, "Please enter your link").replyMarkup(new ForceReply()));
        } else {
            trackExecutor.execute(currentUser, chatId, message);
        }
    }
}
