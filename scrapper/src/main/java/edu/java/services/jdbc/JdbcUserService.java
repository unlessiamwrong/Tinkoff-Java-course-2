package edu.java.services.jdbc;

import edu.java.domain.jdbc.User;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.repositories.jdbc.JdbcUserRepository;
import edu.java.services.UserService;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
public class JdbcUserService implements UserService {

    private final JdbcUserRepository jdbcUserRepository;

    @Override
    public void add(long chatId) {
        User user = jdbcUserRepository.getUserByChatId(chatId);
        if (user != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + chatId + "' is already registered");
        }
        jdbcUserRepository.add(new User(chatId));

    }

    @Override
    public void remove(long chatId) {
        User user = jdbcUserRepository.getUserByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }
        jdbcUserRepository.remove(new User(user.getId(), chatId));
    }
}
