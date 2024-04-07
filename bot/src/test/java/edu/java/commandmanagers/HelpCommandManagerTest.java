package edu.java.commandmanagers;

import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HelpCommandManagerTest extends AbstractIntegrationManagersTest {

    @BeforeEach
    void setupMocks() {
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
    }

    @Test
    void whenUseHelpCommand_SendAllCommands() {
        //Arrange
        when(helpCommand.execute()).thenReturn("These are all available commands:");

        // Act
        helpCommandManager.startProcess(message);
        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(bot).execute(captor.capture());
        Map<String, Object> params = captor.getValue().getParameters();

        // Assert
        assertThat(params).containsEntry("text", "These are all available commands:");

    }

}
