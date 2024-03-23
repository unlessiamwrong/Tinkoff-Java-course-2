package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.responses.github.commits.GitHubCommitResponse;
import edu.java.dto.responses.github.repository.GitHubRepositoryResponse;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;

public class GitHubClientTest extends AbstractIntegrationTest {
    @Test
    public void when_UseGETRepository_ToGitHubRepository_ReturnMockBody() {
        //Arrange
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/test/test"))
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
    public void when_UseGETCommits_ToGitHubRepository_ReturnMockBody() {
        //Arrange
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/test/test/commits"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsArray.toString())));

        //Act
        List<GitHubCommitResponse> response = gitHubClient.getCommits("test", "test");

        //Assert
        assertThat(response).isNotEmpty();
    }
}
