package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.commands.commandmanagers.HelpCommandManager;
import edu.java.bot.commands.commandmanagers.ListCommandManager;
import edu.java.bot.commands.commandmanagers.StartCommandManager;
import edu.java.bot.commands.commandmanagers.TrackCommandManager;
import edu.java.bot.commands.commandmanagers.UntrackCommandManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public abstract class AbstractIntegrationManagersTest {

    @Mock
    protected Message message;
    @Mock
    protected Chat chat;
    @MockBean
    protected TelegramBot bot;
    @MockBean
    protected StartCommand startCommand;
    @MockBean
    protected HelpCommand helpCommand;
    @MockBean
    protected TrackCommand trackCommand;
    @MockBean
    protected ListCommand listCommand;
    @MockBean
    protected UntrackCommand untrackCommand;
    @Autowired
    protected StartCommandManager startCommandManager;
    @Autowired
    protected HelpCommandManager helpCommandManager;
    @Autowired
    protected TrackCommandManager trackCommandManager;
    @Autowired
    protected UntrackCommandManager untrackCommandManager;
    @Autowired
    protected ListCommandManager listCommandManager;

}
