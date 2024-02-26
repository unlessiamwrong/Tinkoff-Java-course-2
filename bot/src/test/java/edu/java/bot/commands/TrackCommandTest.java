package edu.java.bot.commands;

import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TrackCommandTest {

    @Autowired
    TrackCommand trackCommand;

    @Test
    public void whenUseTrackCommand_AddLinkToUserLinks() {
        //Arrange
        User user = new User(1L);
        Link link = new Link("UrlStub", "InfoStub");

        //Act
        trackCommand.execute(user, link);

        //Assert
        assertThat(user.links).isNotEmpty();

    }
}
