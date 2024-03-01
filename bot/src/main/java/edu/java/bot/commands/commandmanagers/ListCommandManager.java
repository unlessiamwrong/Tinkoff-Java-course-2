package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.repositories.Link;
import edu.java.bot.repositories.User;
import edu.java.bot.repositories.UserRepository;
import edu.java.bot.utilities.others.UserNotRegisteredResponse;
import java.util.List;
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
        Long chatId = message.chat().id();
        User currentUser = userRepository.get(chatId);
        if (currentUser == null) {
            userNotRegisteredResponse.send(chatId);
        } else {

            List<Link> currentLinks = listCommand.execute(currentUser);
            if (!currentLinks.isEmpty()) {
                String responce = "Your current tracked links: \n";
                int orderCount = 1;
                for (Link link : currentLinks) {
                    responce += orderCount + ". " + link.url + "\n";
                    orderCount++;
                }
                bot.execute(new SendMessage(chatId, responce));
            } else {
                bot.execute(new SendMessage(chatId, "List is empty. You can add links with command /track"));

            }
        }
    }
}
