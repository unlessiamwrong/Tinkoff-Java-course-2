package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.TelegramBotCommand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    private final ApplicationConfig applicationConfig;

    @Autowired BotConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public TelegramBot buildBot() {
        return new TelegramBot(applicationConfig.telegramToken());
    }

    @Bean
    public SetMyCommands myCommands(List<TelegramBotCommand> myCommands) {
        List<BotCommand> commandsToBot = myCommands.stream().map(TelegramBotCommand::toApiCommand).toList();
        return new SetMyCommands(commandsToBot.toArray(BotCommand[]::new));
    }
}

