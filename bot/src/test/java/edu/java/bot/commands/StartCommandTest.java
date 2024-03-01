package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends AbstractIntegrationTest {
    @Mock
    Message message;
    @Mock
    Chat chat;

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
