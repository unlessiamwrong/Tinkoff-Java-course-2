package edu.java.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.commands.TelegramBotCommand;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
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

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }
}

