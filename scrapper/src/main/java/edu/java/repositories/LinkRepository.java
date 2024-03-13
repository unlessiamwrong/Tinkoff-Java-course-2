package edu.java.repositories;

import edu.java.domain.Link;
import edu.java.domain.User;
import edu.java.utilities.GetLinkData;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
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

    public Link remove(long userId, Link link) {
        jdbcTemplate.update("DELETE FROM user_links WHERE (user_id, link_id)=(?,?)", userId, link.getId());
        List<Long> possibleUserLinkRelation =
            jdbcTemplate.queryForList("SELECT user_id FROM user_links WHERE link_id=?", Long.class, link.getId());
        if (possibleUserLinkRelation.isEmpty()) {
            jdbcTemplate.update("DELETE FROM links WHERE id=?", link.getId());
        }
        return link;
    }

    public List<Link> findAllUserLinks(long userId) {
        return jdbcTemplate.query(
            "SELECT l.* FROM links l INNER JOIN user_links ul ON l.id = ul.link_id WHERE ul.user_id=?",
            new Object[] {userId},
            (rs, row) -> {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                return new Link(id, name);
            }
        );
    }



    public Link getLinkFromUser(long userId, URI url) {
        String urlString = url.toString();
        List<Long> listLinkIdFromLinks = jdbcTemplate.queryForList(
            "SELECT id FROM links WHERE name=?",
            Long.class,
            urlString
        );
        if (listLinkIdFromLinks.isEmpty()) {
            return null;
        }

        List<Long> listLinkIdFromUserLinks = jdbcTemplate.queryForList(
            "SELECT link_id FROM user_links WHERE (user_id, link_id)=(?,?)",
            Long.class,
            userId,
            listLinkIdFromLinks.getFirst()
        );

        if (listLinkIdFromUserLinks.isEmpty()) {
            return null;
        }
        return new Link(listLinkIdFromUserLinks.getFirst(), urlString);

    }

    public List<Link> findAll() {
        return jdbcTemplate.query(
            "SELECT id, name, last_update, last_check_for_update FROM links WHERE last_check_for_update IS NULL OR last_check_for_update < ?",
            (rs, row) -> new Link(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getTimestamp("last_update").toLocalDateTime().atOffset(ZoneOffset.UTC),
                rs.getTimestamp("last_check_for_update") != null ?
                    rs.getTimestamp("last_check_for_update").toInstant().atOffset(ZoneOffset.UTC) : null
            ),
            OffsetDateTime.now().minusMinutes(1)
        );

    }

    public List<Long> updateLinkGetRelatedUsers(
        long linkId,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheckForUpdate
    ) {

        jdbcTemplate.update(
            "UPDATE links SET last_update = ?, last_check_for_update = ? WHERE id = ?",
            lastUpdate,
            lastCheckForUpdate,
            linkId
        );
        return jdbcTemplate.queryForList("SELECT user_id FROM user_links WHERE link_id=?", Long.class, linkId);
    }
}
