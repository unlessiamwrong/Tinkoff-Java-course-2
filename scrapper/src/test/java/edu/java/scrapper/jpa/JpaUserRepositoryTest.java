package edu.java.scrapper.jpa;

import edu.java.domain.jpa.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JpaUserRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User();

    @BeforeEach
    void setupChatId() {
        user.setChatId(1L);
    }

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jpaUserRepository.save(user);
        List<User> users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        jpaUserRepository.delete(user);
        List<User> users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }

    @Test
    void whenUse_FindAll_AndUsersExist_ReturnAllFromUsers() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        List<User> users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnEmptyList() {
        //Act
        List<User> users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}

