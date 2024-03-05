package edu.java.configuration;

import edu.java.clients.BotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Bean
    BotClient botClient(@Value("${app.bot_base_url}") String botClientBaseUrl) {
        WebClient client = WebClient.builder().baseUrl(botClientBaseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(BotClient.class);
    }
}
