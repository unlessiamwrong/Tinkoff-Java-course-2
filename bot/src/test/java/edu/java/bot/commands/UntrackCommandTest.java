//package edu.java.bot.commands;
//
//import edu.java.bot.AbstractIntegrationTest;
//import edu.java.bot.dto.responses.GitHubRepositoryResponse;
//import edu.java.bot.repositories.Link;
//import edu.java.bot.repositories.User;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class UntrackCommandTest extends AbstractIntegrationTest {
//    @Test
//    void whenUseUntrackCommand_RemoveLinkFromUserLinks() {
//        //Arrange
//        User user = new User(1L);
//        Link link = new Link("UrlStub", new GitHubRepositoryResponse());
//        user.addLink(link);
//
//        //Act
//        boolean executionIsSuccessful = untrackCommand.execute(user, link.url);
//
//        //Assert
//        assertThat(executionIsSuccessful).isTrue();
//
//    }
//}
