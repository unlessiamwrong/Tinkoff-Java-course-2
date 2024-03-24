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
class ListCommandManagerTest extends AbstractIntegrationManagersTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(listCommand.execute(anyLong())).thenReturn("You are not registered. To do so use /start command");

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "You are not registered. To do so use /start command");

    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_SendAllLinks() {
        //Arrange
        when(listCommand.execute(anyLong())).thenReturn("Your current tracked links:");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "Your current tracked links:");
    }

    @Test
    void whenUserRegistered_AndLinksEmpty_SendCorrectResponse() {
        //Arrange
        when(listCommand.execute(anyLong())).thenReturn("List is empty. You can add links with command /track");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "List is empty. You can add links with command /track");
    }
}
