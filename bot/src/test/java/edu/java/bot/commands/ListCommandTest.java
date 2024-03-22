//package edu.java.bot.commands;
//
//import java.util.List;
//import edu.java.bot.commandManagers.AbstractIntegrationCommandsTest;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ListCommandTest extends AbstractIntegrationCommandsTest {
//
//    @Test
//    void whenUseListCommand_ReturnUserLinks() {
//        //Arrange
//        User user = new User(1L);
//        user.addLink(new Link("UrlStubOne", new GitHubRepositoryResponse()));
//        user.addLink(new Link("UrlStubTwo", new GitHubRepositoryResponse()));
//        user.addLink(new Link("UrlStubThree", new GitHubRepositoryResponse()));
//
//        //Act
//        List<Link> links = listCommand.execute(user);
//
//        //Assert
//        assertThat(links).hasSize(3);
//
//    }
//}
