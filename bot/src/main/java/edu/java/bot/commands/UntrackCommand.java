package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.Users;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("/untrack")
@Order(4)
@SuppressWarnings("MultipleStringLiterals")
public class UntrackCommand implements Command {

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String startProcess(Message message, TelegramBot bot) {
        Long chatId = message.chat().id();
        if (message.text().equals("/untrack")) {
            ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup("Back");
            for (String link : Users.dataBase.get(chatId)) {
                keyboard.addRow(new KeyboardButton(link));
            }
            List<String> currentLinks = Users.dataBase.get(chatId);
            if (!currentLinks.isEmpty()) {
                String responseText = "Please choose link to untrack \n \n" + "Your current tracked links: \n";
                int orderCount = 1;
                for (String link : currentLinks) {
                    responseText += orderCount + ". " + link + "\n";
                    orderCount++;
                }
                SendMessage response =
                    new SendMessage(chatId, responseText).replyMarkup(new ForceReply());

                bot.execute(response);

            } else {
                return "There are no links that are tracked. You can add links with command /track .";
            }

            return "";

        } else {
            List<String> currentLinks = Users.dataBase.get(chatId);

            if (currentLinks.remove(message.text())) {
                return "Link untracked successfully";
            } else {
                return "There is no such link. Please choose from current /list .";
            }

        }
    }

    @Override
    public String description() {
        return "Stop tracking link";
    }

}
