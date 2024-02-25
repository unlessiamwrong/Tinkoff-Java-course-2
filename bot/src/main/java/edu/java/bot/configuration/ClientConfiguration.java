package edu.java.bot.configuration;

import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    @Bean
    GitHubClient gitHubClient() {
        WebClient client = WebClient.builder().baseUrl("https://api.github.com").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOverflowClient StackOverflowClient() {
        WebClient client = WebClient.builder().baseUrl("https://api.stackexchange.com").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(StackOverflowClient.class);
    }
}
