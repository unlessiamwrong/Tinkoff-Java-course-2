package edu.java.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOfClient;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.commands.commandmanagers.HelpCommandManager;
import edu.java.bot.commands.commandmanagers.ListCommandManager;
import edu.java.bot.commands.commandmanagers.StartCommandManager;
import edu.java.bot.commands.commandmanagers.trackcommandmanager.TrackCommandManager;
import edu.java.bot.commands.commandmanagers.untrackcommandmanager.UntrackCommandManager;
import edu.java.bot.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class AbstractIntegrationTest {

    private static final WireMockServer wireMockServer = new WireMockServer();
    @MockBean
    protected TelegramBot bot;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected GitHubClient gitHubClient;
    @Autowired
    protected StackOfClient stackOfClient;
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
    @Autowired
    protected StartCommand startCommand;
    @Autowired
    protected TrackCommand trackCommand;
    @Autowired
    protected ListCommand listCommand;
    @Autowired
    protected UntrackCommand untrackCommand;

    @BeforeAll
    public static void setUp() {
        wireMockServer.start();
    }

    @AfterAll
    public static void shutDown() {
        wireMockServer.stop();
    }

    @AfterEach
    public void reset() {
        wireMockServer.resetAll();
        userRepository.users.clear();
    }
}
