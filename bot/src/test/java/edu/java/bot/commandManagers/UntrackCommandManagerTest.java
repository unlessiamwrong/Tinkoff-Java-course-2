package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UntrackCommandManagerTest extends AbstractIntegrationManagersTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @BeforeEach
    void setMocks() {
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("textStub");
        when(listCommand.execute(anyLong())).thenReturn("");

    }

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        when(untrackCommand.execute(any(Message.class))).thenReturn(
            "You are not registered. To do so use /start command");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "You are not registered. To do so use /start command");

    }

    @Test
    void whenUserRegistered_AndLinksEmpty_SendCorrectResponse() {
        //Arrange
        when(untrackCommand.execute(any(Message.class))).thenReturn(
            "There are no links that are tracked. You can add links with command /track");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry(
            "text",
            "There are no links that are tracked. You can add links with command /track"
        );
    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsUntrackCommand_SendCorrectResponse() {
        //Arrange
        when(untrackCommand.execute(any(Message.class))).thenReturn("Please choose link to untrack");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "Please choose link to untrack");
    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsLink_AndLinkExist_SendCorrectResponse() {
        //Arrange
        when(untrackCommand.execute(any(Message.class))).thenReturn("Link untracked successfully");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert

        assertThat(params).containsEntry("text", "Link untracked successfully");
    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsLink_AndLinkNotExist_SendCorrectResponse() {
        //Arrange
        when(untrackCommand.execute(any(Message.class))).thenReturn(
            "There is no such link. Please choose from current /list");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "There is no such link. Please choose from current /list");
    }

}
