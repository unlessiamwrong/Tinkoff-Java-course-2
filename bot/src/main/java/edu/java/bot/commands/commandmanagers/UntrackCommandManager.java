package edu.java.bot.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.utilities.others.AnalyzeResponse;
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
        String listCommandResponse = listCommand.execute(message.chat().id());
        String errorMessage = AnalyzeResponse.getErrorMessage(listCommandResponse);

        if (errorMessage != null) {
            bot.execute(new SendMessage(message.chat().id(), errorMessage));
        } else if (message.text().equals(COMMAND_NAME)) {
            bot.execute(new SendMessage(
                message.chat().id(),
                "Please choose link to untrack. \n" + listCommandResponse
            ).replyMarkup(new ForceReply()));
        } else {
            bot.execute(new SendMessage(message.chat().id(), untrackCommand.execute(message)));
        }
    }
}
