package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JdbcUserRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User();

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jdbcUserRepository.add(user);
        var users = jdbcTemplate.queryForList("SELECT * FROM users");

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users", Long.class);
        user.setId(userId);

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
        jdbcUserRepository.add(user);

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
