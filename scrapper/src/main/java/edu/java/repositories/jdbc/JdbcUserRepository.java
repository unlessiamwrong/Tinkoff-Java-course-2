package edu.java.repositories.jdbc;

import edu.java.domain.jdbc.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) {
        jdbcTemplate.update("INSERT INTO users(id) VALUES(?)", user.getId());
    }

    public void remove(User user) {
        Long userId = user.getId();
        List<Long> linkIds =
            jdbcTemplate.queryForList("SELECT link_id FROM user_links WHERE user_id=?", Long.class, userId);
        for (Long linkId : linkIds) {
            List<Long> possibleUserLinkRelation =
                jdbcTemplate.queryForList(
                    "SELECT link_id FROM user_links WHERE user_id != ? AND link_id = ?",
                    Long.class,
                    userId,
                    linkId
                );
            if (possibleUserLinkRelation.isEmpty()) {
                jdbcTemplate.update("DELETE FROM user_links WHERE link_id=?", linkId);
                jdbcTemplate.update("DELETE FROM links WHERE id=?", linkId);
            } else {
                jdbcTemplate.update("DELETE FROM user_links WHERE user_id=?", userId);
            }
        }
        jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, row) -> {
            long id = rs.getLong("id");
            return new User(id);
        });
    }

    public User getUser(long userId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id=?",
                new Object[] {userId},
                new BeanPropertyRowMapper<>(User.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
