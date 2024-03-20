package edu.java.configuration.access;

import edu.java.domain.jdbc.Link;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.UserService;
import edu.java.services.jooq.JooqLinkService;
import edu.java.services.jooq.JooqLinkUpdater;
import edu.java.services.jooq.JooqUserService;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {

    private final DSLContext dslContext;
    private final GetLinkDataRepository getLinkDataRepository;
    private final GetLinkDataItems getLinkDataItems;

    @Bean
    public JooqUserRepository jooqUserRepository() {
        return new JooqUserRepository(dslContext);
    }

    @Bean
    public JooqLinkRepository jooqLinkRepository() {
        return new JooqLinkRepository(dslContext, jooqUserRepository(), getLinkDataRepository, getLinkDataItems);
    }

    @Bean
    public UserService userService(
        JooqUserRepository jooqUserRepository
    ) {
        return new JooqUserService(jooqUserRepository);
    }

    @Bean
    public LinkService linkService(
        JooqUserRepository jooqUserRepository,
        JooqLinkRepository jooqLinkRepository
    ) {
        return new JooqLinkService(jooqLinkRepository, jooqUserRepository);
    }

    @Bean
    public LinkUpdater<Link> linkUpdater(
        JooqLinkRepository jooqLinkRepository,
        GetLinkDataItems getLinkDataItems
    ) {
        return new JooqLinkUpdater(jooqLinkRepository, getLinkDataItems);
    }

}
