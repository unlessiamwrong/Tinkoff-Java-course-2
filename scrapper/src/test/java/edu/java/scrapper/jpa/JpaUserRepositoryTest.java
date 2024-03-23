package edu.java.scrapper.jpa;

import edu.java.domain.jpa.User;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JpaUserRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User();

    @BeforeEach
    void setupEntities() {
        user.setId(1L);
    }

    @Test
    void whenUse_Add_AddRowToUsers() {
        //Act
        jpaUserRepository.save(user);
        User currentUser = jpaUserRepository.findById(1L).orElse(null);

        //Assert
        assertThat(currentUser.getId()).isEqualTo(1);

    }

    @Test
    void whenUse_Remove_DeleteRowFromUsers() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        jpaUserRepository.delete(user);
        User currentUser = jpaUserRepository.findById(1L).orElse(null);

        //Assert
        assertThat(currentUser).isNull();

    }

    @Test
    void whenUse_FindAll_AndUsersExist_ReturnAllFromUsers() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        var users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).hasSize(1);

    }

    @Test
    void whenUse_FindAll_AndUsersDontExist_ReturnEmptyList() {
        //Act
        var users = jpaUserRepository.findAll();

        //Assert
        assertThat(users).isEmpty();

    }
}

