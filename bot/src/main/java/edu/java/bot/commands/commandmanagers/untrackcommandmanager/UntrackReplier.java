package edu.java.bot.commands.commandmanagers.untrackcommandmanager;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.Link;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackReplier {

    private final TelegramBot bot;

    @Autowired
    public UntrackReplier(TelegramBot bot) {
        this.bot = bot;

    }

    public void execute(Long chatId, List<Link> currentLinks) {
        if (!currentLinks.isEmpty()) {
            String responseText = "Please choose link to untrack \n" + "Your current tracked links: \n";
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
}
