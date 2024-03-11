package edu.java.repositories;

import edu.java.domain.Link;
import edu.java.domain.User;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import edu.java.utilities.GetLinkData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class LinkRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    private final GetLinkData getLinkData;

    @Autowired LinkRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, GetLinkData getLinkData) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.getLinkData = getLinkData;
    }

    public Link add(long userId, URI url) {
        User user = userRepository.getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            OffsetDateTime lastUpdateAt = getLinkData.execute(urlString);
            jdbcTemplate.update(
                "INSERT INTO links(name, last_update) VALUES(?,?) ON CONFLICT (name) DO NOTHING",
                urlString,
                lastUpdateAt
            );

            Long linkId = jdbcTemplate.queryForObject(
                "SELECT id FROM links WHERE name = ?",
                Long.class,
                urlString
            );

            if (linkId != null) {
                link = new Link(linkId, urlString, lastUpdateAt);
                jdbcTemplate.update(
                    "INSERT INTO user_links(user_id, link_id) VALUES(?,?) ON CONFLICT (user_id, link_id) DO NOTHING ",
                    userId,
                    linkId
                );
            }
        }
        return link;
    }

    public Link remove(long userId, URI url) {
        User user = userRepository.getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            Long linkId =
                jdbcTemplate.queryForObject("DELETE FROM links WHERE name=? RETURNING id", Long.class, urlString);
            link = new Link(linkId, urlString);
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
                return new Link(id, name);
            }
        );
    }


}
