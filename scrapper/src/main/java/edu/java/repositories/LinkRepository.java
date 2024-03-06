package edu.java.repositories;

import edu.java.domain.Link;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LinkRepository implements Repository<Link> {


    JdbcTemplate jdbcTemplate;

    @Autowired
    LinkRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public void add(Link link) {
        jdbcTemplate.update("INSERT INTO links(name, value) VALUES(?,?)", link.getName(), link.getValue());

    }

    @Override public void remove(Link link) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", link.getId());

    }

    @Override public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", (rs, row) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String value = rs.getString("value");
            return new Link(id, name, value);
        });
    }
}
