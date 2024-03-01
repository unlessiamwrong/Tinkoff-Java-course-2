package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.responses.GitHubRepositoryResponse;
import edu.java.bot.repositories.Link;
import edu.java.bot.repositories.User;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UntrackCommandManagerTest extends AbstractIntegrationTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @AfterEach
    void afterEach() {
        userRepository.users.clear();
    }

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("You are not registered. Please use command /start");

    }

    @Test
    void whenUserRegistered_AndLinksEmpty_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(message.text()).thenReturn("/untrack");
        userRepository.add(chatId);

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo(
            "There are no links that are tracked. You can add links with command /track");

    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsUntrackCommand_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        User user = new User(chatId, new Link("UrlStub", new GitHubRepositoryResponse()));
        userRepository.add(user);
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(message.text()).thenReturn("/untrack");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text").toString().startsWith("Please choose link to untrack")).isTrue();

    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsLink_AndLinkExist_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        User user = new User(chatId, new Link("UrlStub", new GitHubRepositoryResponse()));
        userRepository.add(user);
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(message.text()).thenReturn("UrlStub");

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("Link untracked successfully");

    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_AndMessageTextEqualsLink_AndLinkNotExist_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(message.text()).thenReturn("LinkStub");
        userRepository.add(chatId);

        // Act
        untrackCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("There is no such link. Please choose from current /list");

    }

}
