package edu.java.scrapper;

import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class TestConfig {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DSLContext dslContext;
    @MockBean
    GetLinkDataItems getLinkDataItems;
    @MockBean
    GetLinkDataRepository getLinkDataRepository;

    @Bean
    public JdbcUserRepository jdbcUserRepository() {
        return new JdbcUserRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinkRepository jdbcLinkRepository() {
        return new JdbcLinkRepository(jdbcTemplate, jdbcUserRepository(), getLinkDataRepository, getLinkDataItems);
    }

    @Bean
    public JooqUserRepository jooqUserRepository() {
        return new JooqUserRepository(dslContext);
    }

    @Bean
    public JooqLinkRepository jooqLinkRepository() {
        return new JooqLinkRepository(dslContext, jooqUserRepository(), getLinkDataRepository, getLinkDataItems);
    }

}

