package edu.java.bot.repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

    public final List<User> users = new ArrayList<>();

    public UserRepository() {

    }

    public User get(Long userId) {
        for (User user : users) {
            if (user.id.equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public void add(Long userId) {
        users.add(new User(userId));
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }
}
