package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.BotApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BotApplication.class)
public class GitHubClientTest {

    @Autowired
    GitHubClient gitHubClient;

    @Test
    public void testGitHubClient() {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/test/test"))
            .willReturn(WireMock.aResponse().withBody("Test Body")));

        String response = gitHubClient.getRepository("test", "test");
        System.out.println(response);

    }

}
