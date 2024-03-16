package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Mock
    private GetLinkDataRepository getLinkDataRepository;

    @Mock
    private GetLinkDataItems getLinkDataItems;

    private JdbcLinkRepository jdbcLinkRepository;

    private JdbcUserRepository jdbcUserRepository;

    @BeforeEach
    void setUp() {
        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
        jdbcLinkRepository =
            new JdbcLinkRepository(jdbcTemplate, jdbcUserRepository, getLinkDataRepository, getLinkDataItems);
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
    void reset() {
        jdbcTemplate.update("DELETE FROM user_links");
        jdbcTemplate.update("DELETE FROM links");
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        User user = new User(1);
        Link link = new Link(1, "linkStubOne");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));
        var possibleLinks = jdbcTemplate.queryForList("SELECT * FROM links");

        //Assert
        assertThat(possibleLinks).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        User user = new User(1);
        Link link = new Link(1, "linkStubOne");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));
        var possibleLinks = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(possibleLinks).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        User user = new User(1);
        Link link = new Link(1, "linkStubOne");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        jdbcLinkRepository.remove(user.getId(), link);
        jdbcUserRepository.remove(user);
        var possibleLinks = jdbcTemplate.queryForList("SELECT * FROM links WHERE name=?", link.getName());

        //Assert
        assertThat(possibleLinks).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        User user = new User(1);
        Link link = new Link(1, "linkStubOne");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        jdbcLinkRepository.remove(user.getId(), link);
        jdbcUserRepository.remove(user);
        var possibleLinks = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(possibleLinks).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        User user = new User(1);
        Link linkOne = new Link(1, "linkStubOne");
        Link linkTwo = new Link(2, "linkStubTwo");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(linkOne.getName()));
        jdbcLinkRepository.add(user.getId(), URI.create(linkTwo.getName()));

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(user.getId());

        //Assert
        assertThat(links).hasSize(2);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        User user = new User(1);
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(user.getId());

        //Assert
        assertThat(links).isEmpty();
    }

    @Test
    void whenUse_getLinkFromUser_AndUserHasLink_ReturnLink() {
        //Arrange
        User user = new User(1);
        Link link = new Link(1, "linkStubOne");
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        link = jdbcLinkRepository.getLinkFromUser(user.getId(), URI.create(link.getName()));

        //Assert
        assertThat(link.getName()).isEqualTo("linkStubOne");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        User user = new User(1);
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        Link link = jdbcLinkRepository.getLinkFromUser(user.getId(), URI.create("linkStubOne"));

        //Assert
        assertThat(link).isNull();
    }

}
