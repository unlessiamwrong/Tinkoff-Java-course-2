package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import edu.java.utilities.parser.GitHubLinkParser;
import edu.java.utilities.parser.StackOfLinkParser;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ScrapperConfig {

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    @Bean
    public GitHubLinkParser gitHubLinkParser() {
        return new GitHubLinkParser();
    }

    @Bean
    public StackOfLinkParser stackOfLinkParser() {
        return new StackOfLinkParser();
    }

    @Bean
    public GetLinkDataItems getLinkDataItems() {
        return new GetLinkDataItems(gitHubLinkParser(), stackOfLinkParser(), gitHubClient, stackOfClient);
    }

    @Bean
    public GetLinkDataRepository getLinkDataRepository() {
        return new GetLinkDataRepository(gitHubLinkParser(), stackOfLinkParser(), gitHubClient, stackOfClient);
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

}

