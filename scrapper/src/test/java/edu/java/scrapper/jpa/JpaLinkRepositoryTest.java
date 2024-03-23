package edu.java.scrapper.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import edu.java.scrapper.AbstractIntegrationTest;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JpaLinkRepositoryTest extends AbstractIntegrationTest {

    private final User user = new User();
    private final Link link = new Link();

    @BeforeEach
    void setupEntities(){
        user.setId(1L);
        link.setName("stub");
        link.setLastUpdate(OffsetDateTime.now());
    }

    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        jpaLinkRepository.save(link);
        var links = jpaLinkRepository.findAll();

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        jpaUserRepository.save(user);
        jpaLinkRepository.save(link);

        //Act
        jpaUserRepository.addUserLink(user.getId(), link.getId());
        var userLinks = jpaUserRepository.findAllUserLinksByUserId(user.getId());

        //Assert
        assertThat(userLinks).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        jpaUserRepository.save(user);
        jpaLinkRepository.save(link);

        //Act
        jpaUserRepository.removeUserLink(user.getId(), link.getId());
        jpaUserRepository.delete(user);
        jpaLinkRepository.delete(link);
        var links = jpaLinkRepository.findAll();

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        jpaUserRepository.save(user);
        jpaLinkRepository.save(link);

        //Act
        jpaUserRepository.removeUserLink(user.getId(), link.getId());
        jpaUserRepository.delete(user);
        jpaLinkRepository.delete(link);
        var userLinks = jpaUserRepository.findAllUserLinksByUserId(user.getId());

        //Assert
        assertThat(userLinks).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        jpaUserRepository.save(user);
        jpaLinkRepository.save(link);
        jpaUserRepository.addUserLink(user.getId(), link.getId());

        //Act
        var links = jpaUserRepository.findAllUserLinksByUserId(user.getId());

        //Assert
        assertThat(links).hasSize(1);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        jpaUserRepository.save(user);

        //Act
        List<Link> links = jpaUserRepository.findAllUserLinksByUserId(user.getId());

        //Assert
        assertThat(links).isEmpty();
    }

}
