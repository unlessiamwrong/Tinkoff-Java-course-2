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
        listCommand.execute(message.chat().id());
    }
}
