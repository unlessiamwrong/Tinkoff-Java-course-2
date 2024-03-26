package edu.java.configuration.access;

import edu.java.domain.jdbc.Link;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.UserService;
import edu.java.services.jdbc.JdbcLinkService;
import edu.java.services.jdbc.JdbcLinkUpdater;
import edu.java.services.jdbc.JdbcUserService;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {

    private final JdbcTemplate jdbcTemplate;
    private final GetLinkDataRepository getLinkDataRepository;
    private final GetLinkDataItems getLinkDataItems;

    @Bean
    public JdbcUserRepository jdbcUserRepository() {
        return new JdbcUserRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinkRepository jdbcLinkRepository() {
        return new JdbcLinkRepository(jdbcTemplate, jdbcUserRepository(), getLinkDataRepository, getLinkDataItems);
    }

    @Bean
    public UserService userService(
        JdbcUserRepository jdbcUserRepository
    ) {
        return new JdbcUserService(jdbcUserRepository);
    }

    @Bean
    public LinkService linkService(
        JdbcUserRepository jdbcUserRepository,
        JdbcLinkRepository jdbcLinkRepository
    ) {
        return new JdbcLinkService(jdbcLinkRepository, jdbcUserRepository);
    }

    @Bean
    public LinkUpdater<Link> linkUpdater(
        JdbcLinkRepository jdbcLinkRepository,
        GetLinkDataItems getLinkDataItems
    ) {
        return new JdbcLinkUpdater(jdbcLinkRepository, getLinkDataItems);
    }

}
