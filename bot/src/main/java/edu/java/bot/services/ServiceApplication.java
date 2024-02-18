package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.Command;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("ReturnCount")
@Service public class ServiceApplication {

    @Autowired public Map<String, Command> commands;

    public String analyzeCommand(Message message, TelegramBot bot) {
        Command currentCommand;
        if (message.replyToMessage() != null
            && message.replyToMessage().text().equals("Please enter your link")) {
            currentCommand = commands.get("/track");
            return currentCommand.startProcess(message, bot);
        }
        if (message.replyToMessage() != null
            && message.replyToMessage().text().startsWith("Please choose link to untrack")) {
            currentCommand = commands.get("/untrack");
            return currentCommand.startProcess(message, bot);
        }
        if (message.text().equals("/help")) {
            String response;
            response = "These are all available commands: \n";
            for (String command : commands.keySet()) {
                response += command + " - " + commands.get(command).description().toLowerCase() + "\n";
            }
            return response;
        }
        currentCommand = commands.get(message.text());
        if (currentCommand == null) {
            return "Unknown command, please use available one.";

        }
        return currentCommand.startProcess(message, bot);

    }
}


