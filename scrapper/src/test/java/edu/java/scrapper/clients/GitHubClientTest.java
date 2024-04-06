package edu.java.scrapper.clients;

import edu.java.dto.responses.github.commits.GitHubCommitResponse;
import edu.java.dto.responses.github.repository.GitHubRepositoryResponse;
import edu.java.scrapper.AbstractIntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ArrayNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GitHubClientTest extends AbstractIntegrationTest {

    @Test
    public void whenUse_GetRepository_ReturnMockedBody() {
        //Arrange
        stubFor(get(urlEqualTo("/repos/test/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        GitHubRepositoryResponse response = gitHubClient.getRepository("test", "test");

        //Assert
        assertThat(response).isNotNull();
    }

    @Test
    public void whenUse_GetCommits_ReturnMockedBody() {
        //Arrange
        ArrayNode jsonResponseAsArray = JsonNodeFactory.instance.arrayNode()
            .add(JsonNodeFactory.instance.objectNode().put("stub", "stub"))
            .add(JsonNodeFactory.instance.objectNode().put("stub", "stub"));
        stubFor(get(urlEqualTo("/repos/test/test/commits"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsArray.toString())));

        //Act
        List<GitHubCommitResponse> response = gitHubClient.getCommits("test", "test");

        //Assert
        assertThat(response).isNotEmpty();
    }

    @Test
    public void whenUse_GetRepository_AndServerReturnsException_RetryIsWorking() {
        //Arrange
        stubFor(get(urlEqualTo("/repos/test/test"))
            .inScenario("exception")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withStatus(500)));

        //Act
        assertThrows(WebClientException.class, () -> gitHubClient.getRepository("test", "test"));

        //Assert
        verify(gitHubClient, times(2)).getRepository(anyString(), anyString());
    }

    @Test
    public void whenUse_GetCommits_AndServerReturnsException_RetryIsWorking() {
        //Arrange
        stubFor(get(urlEqualTo("/repos/test/test/commits"))
            .inScenario("exception")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withStatus(500)));

        //Act
        assertThrows(WebClientException.class, () -> gitHubClient.getCommits("test", "test"));

        //Assert
        verify(gitHubClient, times(2)).getCommits(anyString(), anyString());
    }

}
