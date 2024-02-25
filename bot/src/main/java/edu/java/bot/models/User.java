package edu.java.bot.models;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    public final Long id;
    public final List<Link> links = new ArrayList<>();

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, Link link) {
        this.id = id;
        links.add(link);
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void removeLink(Link link) {
        links.remove(link);
    }
}
