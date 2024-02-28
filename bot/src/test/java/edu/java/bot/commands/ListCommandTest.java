package edu.java.bot.commands;

import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.GitHubResponseDTO;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ListCommandTest extends AbstractIntegrationTest {

    @Test
    public void whenUseListCommand_ReturnUserLinks() {
        //Arrange
        User user = new User(1L);
        user.addLink(new Link("UrlStubOne", new GitHubResponseDTO()));
        user.addLink(new Link("UrlStubTwo", new GitHubResponseDTO()));
        user.addLink(new Link("UrlStubThree", new GitHubResponseDTO()));

        //Act
        List<Link> links = listCommand.execute(user);

        //Assert
        assertThat(links).hasSize(3);

    }
}
