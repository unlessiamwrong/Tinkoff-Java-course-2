package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


public class JdbcLinkRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User(1);
    private final Link link = Link.builder().id(1).name("linkStub").build();


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
        assertThat(currentLink.getName()).isEqualTo("linkStub");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        Link link = jdbcLinkRepository.getLinkFromUser(user.getId(), URI.create("linkStub"));

        //Assert
        assertThat(link).isNull();
    }

}
