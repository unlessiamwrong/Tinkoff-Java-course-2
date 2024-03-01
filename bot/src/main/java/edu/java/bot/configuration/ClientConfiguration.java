package edu.java.bot.configuration;

import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOfClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    @Bean
    GitHubClient gitHubClient(@Value("${app.github_base_url}") String gitHubBaseUrl) {
        WebClient client = WebClient.builder().baseUrl(gitHubBaseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOfClient stackOfClient(@Value("${app.stackof_base_url}") String stackOfBaseUrl) {
        WebClient client = WebClient.builder().baseUrl(stackOfBaseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(StackOfClient.class);
    }
}
