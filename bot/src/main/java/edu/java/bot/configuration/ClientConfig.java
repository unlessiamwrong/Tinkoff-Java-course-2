package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Bean
    ScrapperClient scrapperClient(@Value("${app.scrapper_base_url}") String scrapperClientBaseUrl) {
        WebClient client = WebClient.builder().baseUrl(scrapperClientBaseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(ScrapperClient.class);
    }

}
