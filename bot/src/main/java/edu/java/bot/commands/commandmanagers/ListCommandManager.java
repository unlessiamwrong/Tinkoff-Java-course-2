package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommandManager implements CommandManager {

    private final TelegramBot bot;

    private final UserRepository userRepository;

    private final UserNotRegisteredResponse userNotRegisteredResponse;
    private final ListCommand listCommand;

    @Autowired ListCommandManager(
        TelegramBot bot,
        UserRepository userRepository,
        UserNotRegisteredResponse userNotRegisteredResponse,
        ListCommand listCommand
    ) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.userNotRegisteredResponse = userNotRegisteredResponse;
        this.listCommand = listCommand;

    }

    @Override
    public String commandName() {
        return "/list";
    }

    @Override
    public void startProcess(Message message) {
        String response = listCommand.execute(message.chat().id());
        if (response.equals("Your current tracked links: \n")) {
            bot.execute(new SendMessage(message.chat().id(), "List is empty. You can add links with command /track"));
        } else {
            bot.execute(new SendMessage(message.chat().id(), response));
        }
    }
}
