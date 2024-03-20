//package edu.java.bot.commands;
//
//import edu.java.bot.AbstractIntegrationTest;
//import edu.java.bot.dto.responses.GitHubRepositoryResponse;
//import edu.java.bot.repositories.Link;
//import edu.java.bot.repositories.User;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ListCommandTest extends AbstractIntegrationTest {
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
