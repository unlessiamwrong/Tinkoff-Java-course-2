package edu.java.scrapper.jooq;

import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import static edu.java.domain.jooq.Tables.USERS;
import static org.assertj.core.api.Assertions.assertThat;

public class JooqUserRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User();

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jooqUserRepository.add(user);
        var users = context.selectFrom(USERS);

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        jooqUserRepository.add(user);
        user.setId(context.select(USERS.ID).from(USERS).fetchOne().into(Long.class));

        //Act
        jooqUserRepository.remove(user);
        var users = context.selectFrom(USERS);

        //Assert
        assertThat(users).isEmpty();

    }

    @Test
    void whenUse_FindAll_AndUsersExist_ReturnAllFromUsers() {
        //Arrange
        jooqUserRepository.add(user);
        user.setId(context.select(USERS.ID).from(USERS).fetchOne().into(Long.class));

        //Act
        var users = jooqUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnEmptyList() {
        //Act
        List<User> users = jooqUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}

