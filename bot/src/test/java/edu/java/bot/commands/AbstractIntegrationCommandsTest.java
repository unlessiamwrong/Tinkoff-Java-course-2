package edu.java.bot.commandManagers;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.clients.ScrapperClient;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class AbstractIntegrationCommandsTest {

    private static final WireMockServer wireMockServer = new WireMockServer();
    protected static final byte[] NOT_FOUND =
        "{\"description\":\"Not Found\",\"code\":\"404\",\"exceptionName\":\"NotFoundException\",\"exceptionMessage\":\"User is not found\"}".getBytes();
    protected static final byte[] CONFLICT =
        "{\"description\":\"Not Found\",\"code\":\"404\",\"exceptionName\":\"NotFoundException\",\"exceptionMessage\":\"User is already registered\"}".getBytes();
    @MockBean
    protected TelegramBot bot;
    @MockBean
    protected ScrapperClient scrapperClient;
    @Autowired
    protected StartCommand startCommand;
    @Autowired
    protected HelpCommand helpCommand;
    @Autowired
    protected TrackCommand trackCommand;
    @Autowired
    protected ListCommand listCommand;
    @Autowired
    protected UntrackCommand untrackCommand;


    @BeforeAll
    public static void setup() {
        wireMockServer.start();
    }

    @AfterAll
    public static void shutdown() {
        wireMockServer.stop();
    }

    @AfterEach
    public void reset() {
        wireMockServer.resetAll();
    }
}

