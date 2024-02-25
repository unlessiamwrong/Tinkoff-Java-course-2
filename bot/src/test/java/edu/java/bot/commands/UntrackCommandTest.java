package edu.java.bot.commands;


import edu.java.bot.models.Link;
import edu.java.bot.models.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class UntrackCommandTest {



    @Autowired
    UntrackCommand untrackCommand;

    @Test
    public void whenUseUntrackCommand_RemoveLinkFromUserLinks() {
        //Arrange
        User user = new User(1L);
        Link link = new Link("UrlStub", "InfoStub");
        user.addLink(link);


        //Act
        boolean executionIsSuccessful = untrackCommand.execute(user, link.url);

        //Assert
        assertThat(executionIsSuccessful).isTrue();

    }
}
