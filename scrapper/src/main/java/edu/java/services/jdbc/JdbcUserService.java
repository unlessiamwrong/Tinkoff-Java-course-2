package edu.java.services.jdbc;


import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.domain.User;
import edu.java.repositories.UserRepository;
import edu.java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class JdbcUserService implements UserService {

    UserRepository userRepository;

    @Autowired JdbcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(long userId) {
        User user = userRepository.getUser(userId);
        if(user != null){
            throw new UserAlreadyRegisteredException("User with id:" + userId + " is already registered");
        }
        userRepository.add(new User(userId));

    }

    @Override
    public void remove(long userId) {
        User user = userRepository.getUser(userId);
        if(user == null){
            throw new NotFoundException("User with id:" + userId + "is not found");
        }
        userRepository.remove(new User(userId));
    }
}
