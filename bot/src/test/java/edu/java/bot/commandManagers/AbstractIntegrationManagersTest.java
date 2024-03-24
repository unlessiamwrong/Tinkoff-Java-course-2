package edu.java.bot.commandManagers;

import com.pengrad.telegrambot.TelegramBot;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class AbstractIntegrationManagersTest {

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
