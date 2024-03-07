package edu.java.services.jdbc;

import edu.java.domain.User;
import edu.java.repositories.UserRepository;
import edu.java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcUserService implements UserService {

    UserRepository userRepository;

    @Autowired JdbcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(long userId) {
        userRepository.add(new User(userId));

    }

    @Override
    public void remove(long userId) {
        userRepository.remove(new User(userId));
    }
}
