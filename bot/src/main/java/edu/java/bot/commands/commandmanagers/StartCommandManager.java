package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommandManager implements CommandManager {

    private final TelegramBot bot;
    private final UserRepository userRepository;
    private final StartCommand startCommand;

    @Autowired StartCommandManager(TelegramBot bot, UserRepository userRepository, StartCommand startCommand) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.startCommand = startCommand;
    }

    @Override
    public String commandName() {
        return "/start";
    }

    @Override
    public void startProcess(Message message) {
        Long chatId = message.chat().id();
        if (userRepository.get(chatId) != null) {
            bot.execute(new SendMessage(chatId, "You are already registered. To track link use command /track"));
        } else {
            startCommand.execute(chatId);
            bot.execute(new SendMessage(chatId, "You are successfully registered"));
        }
    }
}
