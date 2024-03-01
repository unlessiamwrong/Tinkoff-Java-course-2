package edu.java.bot.commands;

import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.GitHubResponseDTO;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UntrackCommandTest extends AbstractIntegrationTest {
    @Test
    public void whenUseUntrackCommand_RemoveLinkFromUserLinks() {
        //Arrange
        User user = new User(1L);
        Link link = new Link("UrlStub", new GitHubResponseDTO());
        user.addLink(link);

        //Act
        boolean executionIsSuccessful = untrackCommand.execute(user, link.url);

        //Assert
        assertThat(executionIsSuccessful).isTrue();

    }
}
