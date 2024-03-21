package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.AbstractIntegrationTest;
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
public class HelpCommandManagerTest extends AbstractIntegrationTest {

    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void whenUserNotRegistered_SendAllCommands() {
        //Arrange
        Long chatId = 1L;
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);

        // Act
        helpCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params.get("text").toString().startsWith("These are all available commands:")).isTrue();

    }

//    @Test
//    void whenUserRegistered_SendAllCommands() {
//        //Arrange
//        Long chatId = 1L;
//        when(message.chat()).thenReturn(chat);
//        when(message.chat().id()).thenReturn(chatId);
//        userRepository.add(chatId);
//
//        // Act
//        helpCommandManager.startProcess(message);
//        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
//        verify(bot).execute(captor.capture());
//        Map<String, Object> params = captor.getValue().getParameters();
//
//        // Assert
//        assertThat(params.get("text").toString().startsWith("These are all available commands:")).isTrue();
//    }
}

