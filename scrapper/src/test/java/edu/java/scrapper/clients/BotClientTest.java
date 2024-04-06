package edu.java.scrapper.clients;

import edu.java.scrapper.AbstractIntegrationTest;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BotClientTest extends AbstractIntegrationTest {

    @Test
    public void whenUse_PostUpdates_AndServerReturnsException_RetryIsWorking() {
        //Arrange
        stubFor(get(urlEqualTo("/updates"))
            .inScenario("exception")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withStatus(500)));

        //Assert
        assertThrows(WebClientException.class, () -> botClient.getUpdates(new ArrayList<>()));

        //Act
        verify(botClient, times(2)).getUpdates(anyList());
    }
}
