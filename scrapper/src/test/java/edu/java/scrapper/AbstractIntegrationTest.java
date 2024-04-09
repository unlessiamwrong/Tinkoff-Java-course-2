package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.scheduler.Scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Import(TestConfig.class)
public abstract class AbstractIntegrationTest {

    protected static final WireMockServer wireMockServer = new WireMockServer();
    protected static PostgreSQLContainer<?> POSTGRES;
    protected static ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
        .put("stub", "stub")
        .put("stub", "stub");

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        try {
            runMigrations(POSTGRES);
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }

    }


    @MockBean
    PrometheusMeterRegistry prometheusMeterRegistry;
    @MockBean
    protected Scheduler scheduler;
    @SpyBean
    protected BotClient botClient;
    @SpyBean
    protected StackOfClient stackOfClient;
    @SpyBean
    protected GitHubClient gitHubClient;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DSLContext context;
    @Autowired
    protected JpaUserRepository jpaUserRepository;
    @Autowired
    protected JpaLinkRepository jpaLinkRepository;
    @Autowired
    protected JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    protected JdbcUserRepository jdbcUserRepository;
    @Autowired
    protected JooqUserRepository jooqUserRepository;
    @Autowired
    protected JooqLinkRepository jooqLinkRepository;

    private static void runMigrations(JdbcDatabaseContainer<?> c) throws SQLException, LiquibaseException {
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        Database database =
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("/master.yml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @DynamicPropertySource
    private static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeAll
    public static void startWireMock() {
        wireMockServer.start();
    }

    @AfterAll
    public static void shutdownWireMock() {
        wireMockServer.stop();
    }

    @AfterEach
    public void resetDbAndWireMock() {
        jdbcTemplate.update("DELETE FROM user_links");
        jdbcTemplate.update("DELETE FROM links");
        jdbcTemplate.update("DELETE FROM users");
        wireMockServer.resetAll();
    }

}
