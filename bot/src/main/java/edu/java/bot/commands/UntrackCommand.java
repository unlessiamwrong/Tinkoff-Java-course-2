package edu.java.bot.commands;

import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class UntrackCommand implements TelegramBotCommand {

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Stop tracking link";
    }

    public boolean execute(User user, String possibleLink) {
        for (Link link : user.links) {
            if (link.url.equals(possibleLink)) {
                user.removeLink(link);
                return true;
            }
        }
        return false;
    }

}
