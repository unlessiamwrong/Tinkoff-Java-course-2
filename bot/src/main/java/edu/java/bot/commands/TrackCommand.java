package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.Users;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("/track")
@Order(3)
@SuppressWarnings("MultipleStringLiterals")
public class TrackCommand implements Command {

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String startProcess(Message message, TelegramBot bot) {
        Long chatId = message.chat().id();
        if (message.text().equals("/track")) {
            SendMessage request =
                new SendMessage(chatId, "Please enter your link").replyMarkup(new ForceReply());
            bot.execute(request);
            return "";
        } else {

            List<String> currentLinks = Users.dataBase.get(chatId);
            currentLinks.add(message.text());
            return "Link added successfully";

        }
    }

    @Override
    public String description() {
        return "Start tracking link";
    }
}
