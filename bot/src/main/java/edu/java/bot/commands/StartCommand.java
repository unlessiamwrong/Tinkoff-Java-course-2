package edu.java.bot.commands;

import edu.java.bot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartCommand implements TelegramBotCommand {

    private final UserRepository userRepository;

    @Autowired
    public StartCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Registration";
    }

    public void execute(Long userId) {
        userRepository.add(userId);
    }
}
