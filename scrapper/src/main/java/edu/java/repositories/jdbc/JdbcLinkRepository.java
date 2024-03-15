package edu.java.repositories.jdbc;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcLinkRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserRepository jdbcUserRepository;

    private final GetLinkDataItems getLinkDataItems;

    private final GetLinkDataRepository getLinkDataRepository;

    @Autowired JdbcLinkRepository(
        JdbcTemplate jdbcTemplate,
        JdbcUserRepository jdbcUserRepository,
        GetLinkDataItems getLinkDataItems,
        GetLinkDataRepository getLinkDataRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserRepository = jdbcUserRepository;
        this.getLinkDataItems = getLinkDataItems;
        this.getLinkDataRepository = getLinkDataRepository;
    }

    public Link add(long userId, URI url) {
        User user = jdbcUserRepository.getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            DataSet dataSet = getLinkDataItems.execute(urlString);
            OffsetDateTime lastUpdateAt;
            if (dataSet != null) {
                lastUpdateAt = dataSet.dateTime();
            } else {
                lastUpdateAt = getLinkDataRepository.execute(urlString);
            }
            jdbcTemplate.update(
                "INSERT INTO links(name, last_update) VALUES(?,?)",
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
                    "INSERT INTO user_links(user_id, link_id) VALUES(?,?)",
                    userId,
                    linkId
                );
            }
        }
        return link;
    }

    public Link remove(long userId, Link link) {
        Long linkId = link.getId();

        jdbcTemplate.update("DELETE FROM user_links WHERE (user_id, link_id)=(?,?)", userId, linkId);
        List<Long> possibleUserLinkRelation =
            jdbcTemplate.queryForList("SELECT user_id FROM user_links WHERE link_id=?", Long.class, linkId);
        if (possibleUserLinkRelation.isEmpty()) {
            jdbcTemplate.update("DELETE FROM links WHERE id=?", linkId);
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
                rs.getTimestamp("last_update").toInstant().atOffset(ZoneOffset.UTC),
                rs.getTimestamp("last_check_for_update") != null ?
                    rs.getTimestamp("last_check_for_update").toInstant().atOffset(ZoneOffset.UTC) : OffsetDateTime.now()
            ),
            OffsetDateTime.now().minusMinutes(5)
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

    public void updateLinkWithLastCheckForUpdate(
        long linkId,
        OffsetDateTime lastCheckForUpdate
    ) {

        jdbcTemplate.update(
            "UPDATE links SET last_check_for_update = ? WHERE id = ?",
            lastCheckForUpdate,
            linkId
        );
    }
}
