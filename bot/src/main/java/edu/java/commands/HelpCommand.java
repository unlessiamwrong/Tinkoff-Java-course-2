package edu.java.commands;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class HelpCommand implements TelegramBotCommand {

    private final List<TelegramBotCommand> commands;

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "Show all available commands";
    }

    public String execute() {
        StringBuilder response = new StringBuilder("These are all available commands: \n");
        for (TelegramBotCommand command : commands) {
            response.append(command.name()).append(" - ").append(command.description().toLowerCase()).append("\n");
        }
        return response.toString();
    }

}
