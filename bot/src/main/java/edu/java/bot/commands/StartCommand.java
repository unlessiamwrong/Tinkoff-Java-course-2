package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.models.Users;
import java.util.ArrayList;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("/start")
@Order(1)
public class StartCommand implements Command {

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String startProcess(Message message, TelegramBot bot) {
        Users.dataBase.putIfAbsent(message.chat().id(), new ArrayList<>());
        return "You are successfully registered";
    }

    @Override
    public String description() {
        return "Registration";
    }

}
