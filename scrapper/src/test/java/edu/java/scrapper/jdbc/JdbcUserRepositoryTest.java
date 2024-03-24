package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class JdbcUserRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User(1);

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users WHERE id = ?", Long.class, user.getId());

        //Assert
        assertThat(userId).isEqualTo(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        jdbcUserRepository.remove(user);
        List<User> possibleUser = jdbcTemplate.query("SELECT * FROM users", (rs, row) -> {
            long id = rs.getLong("id");
            return new User(id);
        });

        //Assert
        assertThat(possibleUser).isEmpty();

    }

    @Test
    void whenUse_FindAll_AndUsersExist_ReturnAllFromUsers() {
        //Arrange
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());

        //Act
        List<User> users = jdbcUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnEmptyList() {
        //Act
        List<User> users = jdbcUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}
