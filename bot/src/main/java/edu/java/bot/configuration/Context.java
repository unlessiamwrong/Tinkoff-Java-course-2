package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.services.ServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component public class Context implements CommandLineRunner {

    private final TelegramBot bot;
    private final ServiceApplication serviceApplication;

    @Autowired public Context(BotConfig botConfig, ServiceApplication serviceApplication, SetMyCommands botCommands) {
        this.serviceApplication = serviceApplication;
        this.bot = botConfig.buildBot();
        this.bot.execute(botCommands);

    }

    @Override public void run(String... args) {

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message().replyToMessage() == null) {
                    String response = serviceApplication.analyzeCommand(update.message(), bot);
                    bot.execute(new SendMessage(update.message().chat().id(), response));
                } else {
                    String response = serviceApplication.analyzeCommand(update.message(), bot);
                    bot.execute(new SendMessage(update.message().chat().id(), response));
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
