package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackCommandManagerTest extends AbstractIntegrationManagersTest {

    @BeforeEach
    void setupMocks() {
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("stub");
    }

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        when(trackCommand.execute(any(Message.class))).thenReturn("You are not registered. To do so use /start command");

        // Act
        trackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "You are not registered. To do so use /start command");
    }

    @Test
    void whenUserRegistered_AndMessageTextEqualsTrackCommand_SendCorrectResponse() {
        //Arrange
        when(trackCommand.execute(any(Message.class))).thenReturn("Please enter your link");

        // Act
        trackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "Please enter your link");
    }

    @Test
    void whenUserRegistered_AndMessageTextEqualsSupportedLink_SendCorrectResponse() {
        //Arrange
        when(trackCommand.execute(any(Message.class))).thenReturn("Link added successfully");

        // Act
        trackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "Link added successfully");
    }

    @Test
    void whenUserRegistered_AndMessageTextEqualsUnsupportedLink_SendCorrectResponse() {
        //Arrange
        when(trackCommand.execute(any(Message.class))).thenReturn(
            "Unsupported link. Github and StackOverflow are only allowed.");

        // Act
        trackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "Unsupported link. Github and StackOverflow are only allowed.");
    }
}
