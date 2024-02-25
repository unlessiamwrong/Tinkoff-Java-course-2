package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TelegramBotCommand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandManager implements CommandManager {

    private final TelegramBot bot;
    private final List<TelegramBotCommand> commands;

    @Autowired HelpCommandManager(TelegramBot bot, List<TelegramBotCommand> commands) {
        this.bot = bot;
        this.commands = commands;

    }

    @Override
    public String commandName() {
        return "/help";
    }

    @Override
    public void startProcess(Message message) {
        String response = "These are all available commands: \n";
        for (TelegramBotCommand command : commands) {
            response += command.name() + " - " + command.description().toLowerCase() + "\n";
        }
        bot.execute(new SendMessage(message.chat().id(), response));
    }
}
