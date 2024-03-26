package edu.java.bot.commands;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public abstract class AbstractIntegrationCommandsTest {

    private static final WireMockServer wireMockServer = new WireMockServer();

    @Mock
    protected Message message;
    @Mock
    protected Chat chat;

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

