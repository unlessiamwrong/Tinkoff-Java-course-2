package edu.java.scrapper.jooq;

import edu.java.domain.jooq.tables.records.UsersRecord;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import static edu.java.domain.jooq.Tables.USERS;
import static org.assertj.core.api.Assertions.assertThat;

public class JooqUserRepositoryTest extends AbstractIntegrationTest {

    private final UsersRecord user = new UsersRecord(1L);

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jooqUserRepository.add(user);
        Long userId = context.select(USERS.ID)
            .from(USERS)
            .where(USERS.ID.eq(user.getId()))
            .fetchOne()
            .into(Long.class);

        //Assert
        assertThat(userId).isEqualTo(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, user.getId()).execute();

        //Act
        jooqUserRepository.remove(user);
        var users = context.selectFrom(USERS);

        //Assert
        assertThat(users).isEmpty();

    }

    @Test
    void whenUse_FindAll_AndUsersExist_ReturnAllFromUsers() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, user.getId()).execute();

        //Act
        var users = jooqUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnEmptyList() {
        //Act
        var users = jooqUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}

