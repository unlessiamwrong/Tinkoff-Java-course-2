package edu.java.domain;

import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link {

    private final long id;
    private final String name;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheckForUpdate;

    public Link(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Link(long id, String name, OffsetDateTime lastUpdate) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public Link(long id, String name, OffsetDateTime lastUpdate, OffsetDateTime lastCheckForUpdate) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.lastCheckForUpdate = lastCheckForUpdate;
    }

}
