package edu.java.bot.utilities.others;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.commandmanagers.CommandManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkManager {

    private final Map<String, CommandManager> commandManagerMap = new HashMap<>();
    private final TelegramBot bot;

    @Autowired
    LinkManager(TelegramBot bot, List<CommandManager> commandManagerList) {
        this.bot = bot;
        for (CommandManager commandManager : commandManagerList) {
            commandManagerMap.put(commandManager.commandName(), commandManager);
        }
    }

    public void startProcess(Message message) {
        CommandManager currentCommandManager;
        if (message.replyToMessage() != null
            && message.replyToMessage().text().equals("Please enter your link")) {
            currentCommandManager = commandManagerMap.get("/track");
            currentCommandManager.startProcess(message);
        } else if (message.replyToMessage() != null
            && message.replyToMessage().text().startsWith("Please choose link to untrack")) {
            currentCommandManager = commandManagerMap.get("/untrack");
            currentCommandManager.startProcess(message);
        } else {
            bot.execute(new SendMessage(message.chat().id(), "Unknown command, please use available one"));
        }

    }
}
