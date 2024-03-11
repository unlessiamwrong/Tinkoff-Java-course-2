package edu.java.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Link {

    private final long id;
    private final String name;
    private OffsetDateTime lastUpdate;

    public Link(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Link(long id, String name, OffsetDateTime lastUpdate){
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

}
