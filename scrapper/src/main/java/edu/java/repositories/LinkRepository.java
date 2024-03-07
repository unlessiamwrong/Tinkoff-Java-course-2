package edu.java.repositories;

import edu.java.domain.Link;
import edu.java.domain.User;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired LinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Link add(long userId, URI url) {
        User user = getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            jdbcTemplate.update(
                "INSERT INTO links(name, value) VALUES(?,?) ON CONFLICT (name) DO NOTHING",
                urlString,
                urlString
            );

            Long linkId = jdbcTemplate.queryForObject(
                "SELECT id FROM links WHERE name = ?",
                Long.class,
                urlString
            );

            if(linkId != null) {
                link = new Link(linkId, urlString, urlString);
                jdbcTemplate.update("INSERT INTO user_links(user_id, link_id) VALUES(?,?) ON CONFLICT (user_id, link_id) DO NOTHING ", userId, linkId);
            }
        }
        return link;
    }


public Link remove(long userId, URI url) {

        User user = getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            Long linkId =
                jdbcTemplate.queryForObject("DELETE FROM links WHERE name=? RETURNING id", Long.class, urlString);
            link = new Link(linkId, urlString, urlString);
            jdbcTemplate.update("DELETE FROM user_links WHERE link_id=?", linkId);
        }
        return link;
    }

public List<Link> findAll(long userId) {
    return jdbcTemplate.query(
        "SELECT l.* FROM links l INNER JOIN user_links ul ON l.id = ul.link_id WHERE ul.user_id = ?",
        new Object[] {userId},
        (rs, row) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String value = rs.getString("value");
            return new Link(id, name, value);
        }
    );
}

public User getUser(long userId) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users WHERE id=?",
        new Object[] {userId},
        new BeanPropertyRowMapper<>(
            User.class)
    );
}
}
