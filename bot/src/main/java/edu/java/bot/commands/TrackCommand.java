package edu.java.bot.commands;

import edu.java.bot.repositories.Link;
import edu.java.bot.repositories.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class TrackCommand implements TelegramBotCommand {

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Start tracking link";
    }

    public void execute(User user, Link link) {
        user.addLink(link);
    }
}
