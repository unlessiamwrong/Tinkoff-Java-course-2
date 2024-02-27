package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class StackOfClientTest {

    public WireMockServer wireMockServer;
    @Autowired
    StackOfClient stackOfClient;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @AfterEach
    public void shutDown() {
        wireMockServer.stop();
    }

    @Test
    public void when_UseGET_ToStackOfQuestion_ReturnMockBody() {
        //Arrange
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/questions/test?site=stackoverflow"))
            .willReturn(WireMock.aResponse().withBody("Test Body")));

        //Act
        String response = stackOfClient.getQuestion("test", "stackoverflow");

        //Assert
        assertThat(response).isEqualTo("Test Body");

    }
}
