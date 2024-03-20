package edu.java.services.jooq;

import edu.java.domain.jdbc.User;
import edu.java.domain.jooq.tables.records.UsersRecord;
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
    public void add(long userId) {
        User user = jooqUserRepository.getUser(userId);
        if (user != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + userId + "' is already registered");
        }
        jooqUserRepository.add(new UsersRecord(userId));

    }

    @Override
    public void remove(long userId) {
        User user = jooqUserRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }
        jooqUserRepository.remove(new UsersRecord(userId));

    }
}
