package edu.java.services.jooq;

import edu.java.domain.jdbc.User;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.repositories.jooq.JooqUserRepository;
import edu.java.services.UserService;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
public class JooqUserService implements UserService {

    private final JooqUserRepository jooqUserRepository;

    @Override
    public void add(long chatId) {
        User user = jooqUserRepository.getUserByChatId(chatId);
        if (user != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + chatId + "' is already registered");
        }
        jooqUserRepository.add(new User(chatId));

    }

    @Override
    public void remove(long chatId) {
        User user = jooqUserRepository.getUserByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }
        jooqUserRepository.remove(new User(chatId));

    }
}
