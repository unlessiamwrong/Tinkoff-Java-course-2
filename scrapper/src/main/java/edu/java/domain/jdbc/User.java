package edu.java.domain.jdbc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private long id;
    private long chatId;

    public User(long userId, long chatId) {
        this.id = userId;
        this.chatId = chatId;
    }

    public User(long chatId) {
        this.chatId = chatId;
    }
}
