package edu.java.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private final long id;
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
