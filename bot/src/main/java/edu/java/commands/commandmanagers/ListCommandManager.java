package edu.java.commands.commandmanagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.ListCommand;
import edu.java.utilities.others.AnalyzeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommandManager implements CommandManager {

    private final TelegramBot bot;

    private final ListCommand listCommand;

    @Override
    public String commandName() {
        return "/list";
    }

    @Override
    public void startProcess(Message message) {
        String response = listCommand.execute(message.chat().id());
        String errorMessage = AnalyzeResponse.getErrorMessage(response);

        if (errorMessage != null) {
            bot.execute(new SendMessage(message.chat().id(), errorMessage));
        } else if (response.equals("Your current tracked links: \n")) {
            bot.execute(new SendMessage(message.chat().id(), "List is empty. You can add links with command /track"));
        } else {
            bot.execute(new SendMessage(message.chat().id(), response));
        }
    }
}
