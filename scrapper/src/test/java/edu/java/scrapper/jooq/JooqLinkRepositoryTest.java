package edu.java.scrapper.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jooq.tables.records.UsersRecord;
import edu.java.scrapper.AbstractIntegrationTest;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import static edu.java.domain.jooq.Tables.LINKS;
import static edu.java.domain.jooq.Tables.USERS;
import static edu.java.domain.jooq.Tables.USER_LINKS;
import static org.assertj.core.api.Assertions.assertThat;

public class JooqLinkRepositoryTest extends AbstractIntegrationTest {

    private final long userId = 1L;
    private final Link link = Link.builder()
        .id(1)
        .name("linkStubOne")
        .build();

    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();

        //Act
        jooqLinkRepository.add(userId, URI.create(link.getName()));
        var links = context.selectFrom(LINKS);

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();

        //Act
        jooqLinkRepository.add(userId, URI.create(link.getName()));
        var userLinks = context.selectFrom(USER_LINKS);

        //Assert
        assertThat(userLinks).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();
        jooqLinkRepository.add(userId, URI.create(link.getName()));

        //Act
        jooqLinkRepository.remove(userId, link);
        jooqUserRepository.remove(new UsersRecord(userId));
        var links = context.selectFrom(LINKS);

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();
        jooqLinkRepository.add(userId, URI.create(link.getName()));

        //Act
        jooqLinkRepository.remove(userId, link);
        jooqUserRepository.remove(new UsersRecord(userId));
        var userLinks = context.selectFrom(USER_LINKS);

        //Assert
        assertThat(userLinks).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();
        jooqLinkRepository.add(userId, URI.create(link.getName()));

        //Act
        List<Link> links = jooqLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).hasSize(1);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();

        //Act
        List<Link> links = jooqLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).isEmpty();
    }

    @Test
    void whenUse_getLinkFromUser_AndUserHasLink_ReturnLink() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();
        jooqLinkRepository.add(userId, URI.create(link.getName()));

        //Act
        Link currentLink = jooqLinkRepository.getLinkFromUser(userId, URI.create(link.getName()));

        //Assert
        assertThat(currentLink.getName()).isEqualTo("linkStubOne");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        context.insertInto(USERS).set(USERS.ID, userId).execute();

        //Act
        Link currentLink = jooqLinkRepository.getLinkFromUser(userId, URI.create(link.getName()));

        //Assert
        assertThat(currentLink).isNull();
    }

}

