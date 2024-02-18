package Hw1.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.TrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackCommandTest {

    @Mock
    private Message message;
    @Mock
    private TelegramBot bot;

    private TrackCommand trackCommand;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        trackCommand = new TrackCommand();
        message = mock(Message.class);
        when(message.text()).thenReturn("/track");
        when(message.chat()).thenReturn(mock(Chat.class));
    }

    @Test
    public void testStartProcess() {
        String expectedReply = "";
        String actualReply = trackCommand.startProcess(message, bot);

        assertThat(actualReply).isEqualTo(expectedReply);
    }

}
