package edu.java.services;

import com.pengrad.telegrambot.model.Message;
import edu.java.commands.commandmanagers.CommandManager;
import edu.java.utilities.others.LinkManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceApplication {
    private final LinkManager linkManager;

    private final Map<String, CommandManager> commandManagerMap = new HashMap<>();

    @Autowired ServiceApplication(LinkManager linkManager, List<CommandManager> commandManagerList) {
        this.linkManager = linkManager;
        for (CommandManager commandManager : commandManagerList) {
            commandManagerMap.put(commandManager.commandName(), commandManager);
        }

    }

    public void sendCommandRequest(Message message) {
        CommandManager currentCommandManager = commandManagerMap.get(message.text());
        if (currentCommandManager == null) {
            linkManager.startProcess(message);
        } else {
            currentCommandManager.startProcess(message);
        }
    }
}
