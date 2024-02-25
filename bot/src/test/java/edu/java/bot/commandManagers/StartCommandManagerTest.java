package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.commandmanagers.StartCommandManager;
import edu.java.bot.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StartCommandManagerTest {

    @MockBean
    TelegramBot bot;
    @Mock
    Message message;
    @Mock
    Chat chat;

    @Autowired
    StartCommandManager startCommandManager;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void afterEach() {
        userRepository.users.clear();
    }

    @Test
    void whenUserNotRegistered_SendCorrectResponse(){
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);


        // Act
        startCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("You are successfully registered");

    }

    @Test
    void whenUserRegistered_SendCorrectResponse(){
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        userRepository.add(chatId);


        // Act
        startCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text")).isEqualTo("You are already registered. To track link use command /track");

    }
}
