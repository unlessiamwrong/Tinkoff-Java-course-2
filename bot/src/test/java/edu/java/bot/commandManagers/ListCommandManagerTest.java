package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.commandmanagers.HelpCommandManager;
import java.util.Map;
import edu.java.bot.commands.commandmanagers.ListCommandManager;
import edu.java.bot.models.Link;
import edu.java.bot.models.User;
import edu.java.bot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ListCommandManagerTest {

    @MockBean
    TelegramBot bot;
    @Mock
    Message message;
    @Mock
    Chat chat;


    @Autowired
    ListCommandManager listCommandManager;

    @Autowired
    UserRepository userRepository;

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
        User user = new User(chatId, new Link("urlStub", "infoStub"));
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
        assertThat(params.get("text").toString().startsWith("List is empty. You can add links with command /track")).isTrue();
    }
}
