package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @Mock
    Message message;
    @Mock
    Chat chat;

    @Autowired
    StartCommand startCommand;

    @Autowired
    UserRepository userRepository;

    @Test
    void whenUseStartCommand_AddUserToDB() {
        //Arrange
        Long userId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(userId);

        //Act
        startCommand.execute(message.chat().id());

        //Assert
        assertThat(userRepository.get(userId)).isNotNull();

    }
}
