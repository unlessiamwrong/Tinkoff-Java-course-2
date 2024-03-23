package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.repositories.jdbc.JdbcLinkRepository;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.scrapper.AbstractIntegrationTest;
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
public class JdbcLinkRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User(1);
    private final Link link = Link.builder().id(1).name("linkStubOne").build();
    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));
        var links = jdbcTemplate.queryForList("SELECT * FROM links");

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));
        var links = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        jdbcLinkRepository.remove(user.getId(), link);
        jdbcUserRepository.remove(user);
        var links = jdbcTemplate.queryForList("SELECT * FROM links WHERE name=?", link.getName());

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        jdbcLinkRepository.remove(user.getId(), link);
        jdbcUserRepository.remove(user);
        var links = jdbcTemplate.queryForList("SELECT * FROM user_links");

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(user.getId());

        //Assert
        assertThat(links).hasSize(1);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        List<Link> links = jdbcLinkRepository.findAllUserLinks(user.getId());

        //Assert
        assertThat(links).isEmpty();
    }

    @Test
    void whenUse_getLinkFromUser_AndUserHasLink_ReturnLink() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
        jdbcLinkRepository.add(user.getId(), URI.create(link.getName()));

        //Act
        Link currentLink = jdbcLinkRepository.getLinkFromUser(user.getId(), URI.create(link.getName()));

        //Assert
        assertThat(currentLink.getName()).isEqualTo("linkStubOne");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        Link link = jdbcLinkRepository.getLinkFromUser(user.getId(), URI.create("linkStubOne"));

        //Assert
        assertThat(link).isNull();
    }

}
