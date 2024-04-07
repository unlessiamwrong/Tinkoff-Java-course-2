package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.HelpCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommandManager implements CommandManager {

    private final HelpCommand helpCommand;
    private final TelegramBot bot;

    @Override
    public String commandName() {
        return "/help";
    }

    @Override
    public void startProcess(Message message) {
        String response = helpCommand.execute();
        bot.execute(new SendMessage(message.chat().id(), response));
    }
}
