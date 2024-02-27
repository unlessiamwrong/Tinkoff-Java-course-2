package edu.java.bot.commands.commandmanagers.untrackcommandmanager;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackRequestAnalyzer {

    private final UntrackReplier untrackReplier;
    private final UntrackExecutor untrackExecutor;

    @Autowired
    public UntrackRequestAnalyzer(UntrackReplier untrackReplier, UntrackExecutor untrackExecutor) {
        this.untrackReplier = untrackReplier;
        this.untrackExecutor = untrackExecutor;
    }

    public void execute(User currentUser, Long chatId, Message message) {
        List<Link> currentLinks = currentUser.links;
        if (message.text().equals("/untrack")) {
            untrackReplier.execute(chatId, currentLinks);
        } else {
            untrackExecutor.execute(currentUser, chatId, message.text());
        }
    }
}
