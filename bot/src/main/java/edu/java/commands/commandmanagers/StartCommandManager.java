package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.StartCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommandManager implements CommandManager {

    private final TelegramBot bot;
    private final StartCommand startCommand;

    @Override
    public String commandName() {
        return "/start";
    }

    @Override
    public void startProcess(Message message) {
        String response = startCommand.execute(message.chat().id());
        bot.execute(new SendMessage(message.chat().id(), response));
    }
}
