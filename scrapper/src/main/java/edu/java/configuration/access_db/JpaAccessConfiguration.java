package edu.java.configuration.access_db;

import edu.java.domain.jpa.Link;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.services.LinkService;
import edu.java.services.LinkUpdater;
import edu.java.services.UserService;
import edu.java.services.jpa.JpaLinkService;
import edu.java.services.jpa.JpaLinkUpdater;
import edu.java.services.jpa.JpaUserService;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public UserService userService(
        JpaUserRepository jpaUserRepository,
        JpaLinkRepository jpaLinkRepository
    ) {
        return new JpaUserService(jpaUserRepository, jpaLinkRepository);
    }

    @Bean
    public LinkService linkService(
        JpaUserRepository jpaUserRepository,
        JpaLinkRepository jpaLinkRepository,
        GetLinkDataItems getLinkDataItems,
        GetLinkDataRepository getLinkDataRepository
    ) {
        return new JpaLinkService(jpaUserRepository, jpaLinkRepository, getLinkDataItems, getLinkDataRepository);
    }

    @Bean
    public LinkUpdater<Link> linkUpdater(
        JpaLinkRepository jpaLinkRepository,
        GetLinkDataItems getLinkDataItems
    ) {
        return new JpaLinkUpdater(jpaLinkRepository, getLinkDataItems);
    }

}
