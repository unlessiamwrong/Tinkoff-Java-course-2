package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.models.Users;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("/list")
@Order(5)
public class ListCommand implements Command {

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String startProcess(Message message, TelegramBot bot) {
        Long chatId = message.chat().id();
        if (!Users.dataBase.get(chatId).isEmpty()) {
            List<String> currentLinks = Users.dataBase.get(chatId);
            String responce = "Your current tracked links: \n";
            int orderCount = 1;
            for (String link : currentLinks) {
                responce += orderCount + ". " + link + "\n";
                orderCount++;
            }
            return responce;

        } else {
            return "List is empty. You can add links with command /track .";
        }

    }

    @Override
    public String description() {
        return "Show all tracked links";
    }

}
