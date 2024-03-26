package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class JdbcLinkRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User(1);
    private final Link link = Link.builder().name("linkStub").build();

    private final DataSet dataSet = new DataSet(
        OffsetDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
        "authorStub",
        "activityStub"
    );

    @Autowired
    GetLinkDataRepository getLinkDataRepository;

    @Autowired
    GetLinkDataItems getLinkDataItems;

    @BeforeEach
    void setup() {
        when(getLinkDataRepository.execute("linkStub")).thenReturn(dataSet.dateTime());
        when(getLinkDataItems.execute("linkStub")).thenReturn(dataSet);
    }

    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT chat_id FROM users", Long.class);

        //Act
        jdbcLinkRepository.add(userId, URI.create(link.getName()));
        var links = jdbcTemplate.queryForList("SELECT * FROM links");

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT chat_id FROM users", Long.class);

        //Act
        jdbcLinkRepository.add(userId, URI.create(link.getName()));
        var links = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        jdbcUserRepository.add(user);
        jdbcLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        link.setId(jdbcTemplate.queryForObject("SELECT id FROM links", Long.class));
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        jdbcLinkRepository.remove(userId, link);

        var links = jdbcTemplate.queryForList("SELECT * FROM links WHERE name=?", link.getName());

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        jdbcUserRepository.add(user);
        jdbcLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        link.setId(jdbcTemplate.queryForObject("SELECT id FROM links", Long.class));
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        jdbcLinkRepository.remove(userId, link);
        var links = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        jdbcUserRepository.add(user);
        jdbcLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        link.setId(jdbcTemplate.queryForObject("SELECT id FROM links", Long.class));
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).hasSize(1);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).isEmpty();
    }

    @Test
    void whenUse_getLinkFromUser_AndUserHasLink_ReturnLink() {
        //Arrange
        jdbcUserRepository.add(user);
        jdbcLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        Link currentLink = jdbcLinkRepository.getLinkFromUser(userId, URI.create(link.getName()));

        //Assert
        assertThat(currentLink.getName()).isEqualTo("linkStub");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);

        //Act
        Link link = jdbcLinkRepository.getLinkFromUser(userId, URI.create("linkStub"));

        //Assert
        assertThat(link).isNull();
    }

}
