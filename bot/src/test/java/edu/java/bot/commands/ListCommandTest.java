package edu.java.bot.commands;


import edu.java.bot.models.Link;
import edu.java.bot.models.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ListCommandTest {



    @Autowired
    ListCommand listCommand;

    @Test
    public void whenUseListCommand_ReturnUserLinks() {
        //Arrange
        User user = new User(1L);
        user.addLink(new Link("UrlStubOne", "InfoStubOne"));
        user.addLink(new Link("UrlStubTwo", "InfoStubTwo"));
        user.addLink(new Link("UrlStubThree", "InfoStubThree"));


        //Act
        List<Link> links = listCommand.execute(user);

        //Assert
        assertThat(links.size()).isEqualTo(3);

    }
}
