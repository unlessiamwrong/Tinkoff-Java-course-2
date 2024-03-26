package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.utilities.others.AnalyzeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        String trackCommandResponse = trackCommand.execute(message);
        String errorMessage = AnalyzeResponse.getErrorMessage(trackCommandResponse);

        if (errorMessage != null) {
            bot.execute(new SendMessage(message.chat().id(), errorMessage));
        } else if (message.text().equals(COMMAND_NAME)) {
            bot.execute(new SendMessage(message.chat().id(), "Please enter your link").replyMarkup(new ForceReply()));
        } else {
            bot.execute(new SendMessage(message.chat().id(), trackCommandResponse));
        }
    }
}
