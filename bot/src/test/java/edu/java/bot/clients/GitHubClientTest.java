package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;

public class GitHubClientTest {

    GitHubClient gitHubClient;
    private WireMockServer wireMockServer;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @Test
    public void testGitHubClient() {

        when(gitHubClient.getRepository("testOwner", "testRepo")).thenReturn("Mocked Response");

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/testOwner/testRepo"))
            .willReturn(WireMock.aResponse().withBody("Test Body")));

        String response = gitHubClient.getRepository("testOwner", "testRepo");

    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }
}
