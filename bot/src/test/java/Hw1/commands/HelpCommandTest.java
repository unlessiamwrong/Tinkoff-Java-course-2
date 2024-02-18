package Hw1.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    @Mock
    private Message message;
    @Mock
    private TelegramBot bot;

    @BeforeEach
    public void setup() {
        message = mock(Message.class);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(mock(Chat.class));
    }

    @Test
    public void testStartProcess() {
        HelpCommand helpCommand = new HelpCommand();
        String expected = "";
        String actual = helpCommand.startProcess(message, bot);

        assertThat(actual).isEqualTo(expected);
    }

}
