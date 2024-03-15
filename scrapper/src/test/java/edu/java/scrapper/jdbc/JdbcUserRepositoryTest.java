package edu.java.scrapper.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcUserRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void whenUse_Add_AddRowToDB() {
        JdbcUserRepository jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
        jdbcUserRepository.add(new User(55));
        Long userId = jdbcTemplate.queryForObject("SELECT id FROM users WHERE id = ?", Long.class, 55);
        assertThat(userId).isEqualTo(55);

    }
}
