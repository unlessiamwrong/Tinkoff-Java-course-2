package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.ListCommand;
import edu.java.commands.UntrackCommand;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommandManager implements CommandManager {

    private static final String COMMAND_NAME = "/untrack";
    private final TelegramBot bot;
    private final UntrackCommand untrackCommand;
    private final ListCommand listCommand;

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void startProcess(Message message) {
        Long userId = message.chat().id();
        String text = message.text();
        if (text.equals(COMMAND_NAME)) {
            bot.execute(new SendMessage(userId, "Please enter your link to untrack").replyMarkup(new ForceReply()));
        } else {
            untrackCommand.execute(userId, URI.create(text));
        }
    }
}
