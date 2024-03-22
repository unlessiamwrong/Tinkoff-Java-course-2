package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartCommandManagerTest extends AbstractIntegrationManagersTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(startCommand.execute(anyLong())).thenReturn("You are successfully registered");

        // Act
        startCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "You are successfully registered");

    }

    @Test
    void whenUserRegistered_SendCorrectResponse() {
        //Arrange
        when(startCommand.execute(anyLong())).thenReturn("User is already registered");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);

        // Act
        startCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "User is already registered");

    }
}
