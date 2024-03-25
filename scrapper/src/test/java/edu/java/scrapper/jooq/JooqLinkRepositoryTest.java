package edu.java.scrapper.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.scrapper.AbstractIntegrationTest;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static edu.java.domain.jooq.Tables.LINKS;
import static edu.java.domain.jooq.Tables.USERS;
import static edu.java.domain.jooq.Tables.USER_LINKS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class JooqLinkRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    GetLinkDataRepository getLinkDataRepository;

    @Autowired
    GetLinkDataItems getLinkDataItems;

    private final User user = new User(1);
    private final Link link = Link.builder()
        .name("linkStub")
        .build();

    @BeforeEach
    void setup() {
        when(getLinkDataRepository.execute("linkStub")).thenReturn(OffsetDateTime.now());
        when(getLinkDataItems.execute("linkStub")).thenReturn(new DataSet(
            OffsetDateTime.now(),
            "authorStub",
            "activityStub"
        ));
    }

    @Test
    void whenUse_Add_AddRowToLinks() {
        //Arrange
        jooqUserRepository.add(user);
        Long userId = context.select(USERS.CHAT_ID).from(USERS).fetchOne().into(Long.class);

        //Act
        jooqLinkRepository.add(userId, URI.create(link.getName()));
        var links = context.selectFrom(LINKS);

        //Assert
        assertThat(links).hasSize(1);

    }

    @Test
    void whenUse_Add_AddRowToUserLinks() {
        //Arrange
        jooqUserRepository.add(user);
        Long userId = context.select(USERS.CHAT_ID).from(USERS).fetchOne().into(Long.class);

        //Act
        jooqLinkRepository.add(userId, URI.create(link.getName()));
        var userLinks = context.selectFrom(USER_LINKS);

        //Assert
        assertThat(userLinks).hasSize(1);

    }

    @Test
    void whenUse_Remove_RemoveRowFromLinks() {
        //Arrange
        jooqUserRepository.add(user);
        jooqLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        link.setId(context.select(LINKS.ID).from(LINKS).fetchOne().into(Long.class));
        Long userId = context.select(USERS.ID).from(USERS).fetchOne().into(Long.class);

        //Act
        jooqLinkRepository.remove(userId, link);
        var links = context.selectFrom(LINKS);

        //Assert
        assertThat(links).hasSize(0);

    }

    @Test
    void whenUse_Remove_RemoveRowFromUserLinks() {
        //Arrange
        jooqUserRepository.add(user);
        jooqLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        link.setId(context.select(LINKS.ID).from(LINKS).fetchOne().into(Long.class));
        Long userId = context.select(USERS.ID).from(USERS).fetchOne().into(Long.class);

        //Act
        jooqLinkRepository.remove(userId, link);
        var userLinks = context.selectFrom(USER_LINKS);

        //Assert
        assertThat(userLinks).hasSize(0);

    }

    @Test
    void whenUse_findAllUserLinks_AndUserHasLinks_ReturnListOfLinks() {
        //Arrange
        jooqUserRepository.add(user);
        jooqLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        Long userId = context.select(USERS.ID).from(USERS).fetchOne().into(Long.class);

        //Act
        List<Link> links = jooqLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).hasSize(1);
    }

    @Test
    void whenUse_findAllUserLinks_AndUserDoesntHaveLinks_ReturnEmptyList() {
        //Arrange
        jooqUserRepository.add(user);
        Long userId = context.select(USERS.CHAT_ID).from(USERS).fetchOne().into(Long.class);

        //Act
        List<Link> links = jooqLinkRepository.findAllUserLinks(userId);

        //Assert
        assertThat(links).isEmpty();
    }

    @Test
    void whenUse_getLinkFromUser_AndUserHasLink_ReturnLink() {
        //Arrange
        jooqUserRepository.add(user);
        jooqLinkRepository.add(user.getChatId(), URI.create(link.getName()));
        Long userId = context.select(USERS.ID).from(USERS).fetchOne().into(Long.class);

        //Act
        Link currentLink = jooqLinkRepository.getLinkFromUser(userId, URI.create(link.getName()));

        //Assert
        assertThat(currentLink.getName()).isEqualTo("linkStub");
    }

    @Test
    void whenUse_getLinkFromUser_AndUserDoesntHaveLink_ReturnNull() {
        //Arrange
        jooqUserRepository.add(user);
        Long userId = context.select(USERS.CHAT_ID).from(USERS).fetchOne().into(Long.class);

        //Act
        Link currentLink = jooqLinkRepository.getLinkFromUser(userId, URI.create(link.getName()));

        //Assert
        assertThat(currentLink).isNull();
    }

}

