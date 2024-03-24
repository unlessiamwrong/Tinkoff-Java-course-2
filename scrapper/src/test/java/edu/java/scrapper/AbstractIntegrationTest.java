package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.repositories.jooq.JooqLinkRepository;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.scheduler.LinkUpdaterScheduler;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.OffsetDateTime;
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
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ArrayNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import static org.mockito.Mockito.when;

@MockBean(LinkUpdaterScheduler.class)
@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public abstract class AbstractIntegrationTest {

    protected static final byte[] NOT_FOUND =
        "{\"description\":\"Not Found\",\"code\":\"404\",\"exceptionName\":\"NotFoundException\",\"exceptionMessage\":\"User is not found\"}".getBytes();
    protected static final byte[] CONFLICT =
        "{\"description\":\"Not Found\",\"code\":\"404\",\"exceptionName\":\"NotFoundException\",\"exceptionMessage\":\"User is already registered\"}".getBytes();
    private static final WireMockServer wireMockServer = new WireMockServer();
    public static PostgreSQLContainer<?> POSTGRES;
    protected static ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode().put("stub", "stub")
        .put("stub", "stub");
    protected static ArrayNode jsonResponseAsArray = JsonNodeFactory.instance.arrayNode()
        .add(JsonNodeFactory.instance.objectNode().put("stub", "stub"))
        .add(JsonNodeFactory.instance.objectNode().put("stub", "stub"));

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

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected DSLContext context;
    @Autowired
    protected StackOfClient stackOfClient;
    @Autowired
    protected GitHubClient gitHubClient;
    @MockBean
    protected GetLinkDataRepository getLinkDataRepository;
    @MockBean
    protected GetLinkDataItems getLinkDataItems;
    protected JdbcLinkRepository jdbcLinkRepository;
    protected JdbcUserRepository jdbcUserRepository;
    protected JooqUserRepository jooqUserRepository;
    protected JooqLinkRepository jooqLinkRepository;
    @Autowired
    protected JpaUserRepository jpaUserRepository;
    @Autowired
    protected JpaLinkRepository jpaLinkRepository;

    private static void runMigrations(JdbcDatabaseContainer<?> c) throws SQLException, LiquibaseException {
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        Database database =
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase("/master.yml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @BeforeAll
    public static void startWireMock() {
        wireMockServer.start();
    }

    @AfterAll
    public static void shutdownWireMock() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @BeforeEach
    void setupRepositories() {
        //Jdbc
        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
        jdbcLinkRepository =
            new JdbcLinkRepository(jdbcTemplate, jdbcUserRepository, getLinkDataRepository, getLinkDataItems);

        //Jooq
        jooqUserRepository = new JooqUserRepository(context);
        jooqLinkRepository =
            new JooqLinkRepository(context, jooqUserRepository, getLinkDataRepository, getLinkDataItems);

        //Mocks
        when(getLinkDataRepository.execute("linkStubOne")).thenReturn(OffsetDateTime.now());
        when(getLinkDataItems.execute("linkStubOne")).thenReturn(new DataSet(
            OffsetDateTime.now(),
            "authorStub",
            "activityStub"
        ));
        when(getLinkDataRepository.execute("linkStubTwo")).thenReturn(OffsetDateTime.now());
        when(getLinkDataItems.execute("linkStubTwo")).thenReturn(new DataSet(
            OffsetDateTime.now(),
            "authorStub",
            "activityStub"
        ));

    }

    @AfterEach
    public void resetDbAndWireMock() {
        jdbcTemplate.update("DELETE FROM user_links");
        jdbcTemplate.update("DELETE FROM links");
        jdbcTemplate.update("DELETE FROM users");
        wireMockServer.resetAll();
    }

}
