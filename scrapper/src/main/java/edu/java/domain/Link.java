package edu.java.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link {

    private final long id;
    private String name;
    private String value;

    public Link(long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
