package edu.java.repositories.jdbc;

import edu.java.domain.jdbc.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class JdbcUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public void add(User user) {
        jdbcTemplate.update("INSERT INTO users(chat_id) VALUES(?)", user.getChatId());
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

    public User getUserByChatId(long userId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE chat_id=?",
                new BeanPropertyRowMapper<>(User.class),
                userId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
