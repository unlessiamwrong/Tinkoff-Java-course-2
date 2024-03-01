package edu.java.bot.commands;

import edu.java.bot.repositories.Link;
import edu.java.bot.repositories.User;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class ListCommand implements TelegramBotCommand {

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "Show all tracked links";
    }

    public List<Link> execute(User user) {
        return user.links;
    }

}
