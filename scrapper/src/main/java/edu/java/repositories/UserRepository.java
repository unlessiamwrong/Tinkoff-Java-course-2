package edu.java.repositories;

import edu.java.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserRepository implements Repository<User> {

    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public void add(User user) {
        jdbcTemplate.update("INSERT INTO users(id, name) VALUES(?,?)", user.getId(), user.getName());
    }

    @Override public void remove(User user) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, row) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new User(id, name);
        });
    }
}
