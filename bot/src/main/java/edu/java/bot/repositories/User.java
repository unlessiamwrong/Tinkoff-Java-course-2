package edu.java.bot.repositories;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

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

    public boolean checkLink(Link possibleLink) {
        for (Link link : links) {
            if (link.url.equals(possibleLink.url)) {
                return true;
            }
        }
        return false;
    }
}
