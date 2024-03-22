package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;

import edu.java.bot.commandManagers.AbstractIntegrationCommandsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest extends AbstractIntegrationCommandsTest {
    @Mock
    Message message;
    @Mock
    Chat chat;

    @Test
    void whenUseStartCommand_ReturnCorrectResponse() {

        doThrow(new WebClientResponseException(404, "CONFLICT", null,CONFLICT, null)).when(scrapperClient).postUser(anyLong());

        //Act
        String response = startCommand.execute(1L);

        assertThat(response).startsWith("User is already registered");


    }
}
