package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.GitHubResponseDTO;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCommandManagerTest extends AbstractIntegrationTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void whenUserNotRegistered_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("You are not registered. Please use command /start");

    }

    @Test
    void whenUserRegistered_AndLinksNotEmpty_SendAllLinks() {
        //Arrange
        Long chatId = 1L;
        User user = new User(chatId, new Link("urlStub", new GitHubResponseDTO()));
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        userRepository.add(user);

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text").toString().startsWith("Your current tracked links")).isTrue();
    }

    @Test
    void whenUserRegistered_AndLinksEmpty_SendCorrectResponse() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        userRepository.add(chatId);

        // Act
        listCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text").toString()
            .startsWith("List is empty. You can add links with command /track")).isTrue();
    }
}
