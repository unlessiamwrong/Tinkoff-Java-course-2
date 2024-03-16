package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.repositories.jdbc.JdbcUserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcUserRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcUserRepository jdbcUserRepository;

    @BeforeEach
    void setUp() {
        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @AfterEach
    void reset() {
        jdbcTemplate.update("DELETE FROM users");
    }

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Arrange
        User user = new User(1);

        //Act
        jdbcUserRepository.add(user);
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users WHERE id = ?", Long.class, user.getId());

        //Assert
        assertThat(userId).isEqualTo(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        User user = new User(1);
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
        User userOne = new User(1);
        User userTwo = new User(2);
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", userOne.getId());
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", userTwo.getId());

        //Act
        List<User> users = jdbcUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(2);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnNull() {
        //Act
        List<User> users = jdbcUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}
