package edu.java.bot.commands.commandmanagers.trackcommandmanager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackExecutor {

    private final TelegramBot bot;
    private final TrackCommand trackCommand;

    private final TrackLinkParser trackLinkParser;

    @Autowired
    public TrackExecutor(TelegramBot bot, TrackCommand trackCommand, TrackLinkParser trackLinkParser) {
        this.bot = bot;
        this.trackCommand = trackCommand;
        this.trackLinkParser = trackLinkParser;
    }

    public void execute(User currentUser, Long chatId, Message message) {
        Link link = trackLinkParser.execute(message.text());
        if (link == null) {
            bot.execute(new SendMessage(chatId, "Unsupported link. Github and StackOverflow are only allowed."));
        } else {
            boolean linkExists = currentUser.checkLink(link);
            if (linkExists) {
                trackCommand.execute(currentUser, link);
                bot.execute(new SendMessage(
                    chatId,
                    "Link is already in track list. Check your current list using /list command"
                ));

            } else {
                bot.execute(new SendMessage(chatId, "Link added successfully"));
            }

        }
    }
}
