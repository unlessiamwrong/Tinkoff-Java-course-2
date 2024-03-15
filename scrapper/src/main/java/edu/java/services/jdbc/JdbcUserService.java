package edu.java.services.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcUserService implements UserService {

    private final JdbcUserRepository jdbcUserRepository;

    @Autowired JdbcUserService(JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public void add(long userId) {
        User user = jdbcUserRepository.getUser(userId);
        if (user != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + userId + "' is already registered");
        }
        jdbcUserRepository.add(new User(userId));

    }

    @Override
    public void remove(long userId) {
        User user = jdbcUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        jdbcUserRepository.remove(new User(userId));
    }
}
