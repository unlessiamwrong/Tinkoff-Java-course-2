package edu.java.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private long id;

    public User(long id) {
        this.id = id;
    }
}
