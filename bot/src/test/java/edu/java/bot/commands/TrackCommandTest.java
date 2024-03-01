package edu.java.bot.commands;

import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.GitHubResponseDTO;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackCommandTest extends AbstractIntegrationTest {
    @Test
    public void whenUseTrackCommand_AddLinkToUserLinks() {
        //Arrange
        User user = new User(1L);
        Link link = new Link("UrlStub", new GitHubResponseDTO());

        //Act
        trackCommand.execute(user, link);

        //Assert
        assertThat(user.links).isNotEmpty();

    }
}
