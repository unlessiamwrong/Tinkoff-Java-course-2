package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TrackCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
@RequiredArgsConstructor
@SuppressWarnings("MissingSwitchDefault")
public class TrackCommandManager implements CommandManager {

    private static final String COMMAND_NAME = "/track";
    private final TelegramBot bot;
    private final TrackCommand trackCommand;

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void startProcess(Message message) {
        Long userId = message.chat().id();
        String text = message.text();
        if (text.equals(COMMAND_NAME)) {
            bot.execute(new SendMessage(userId, "Please enter your link").replyMarkup(new ForceReply()));
        } else {
            trackCommand.execute(userId, URI.create(text));
        }
    }
}
