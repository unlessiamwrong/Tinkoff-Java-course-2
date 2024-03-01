package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.GitHubResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class GitHubClientTest extends AbstractIntegrationTest {

    @Test
    public void when_UseGET_ToGitHubRepository_ReturnMockBody() {
        //Arrange
        ObjectNode jsonResponse = JsonNodeFactory.instance.objectNode();
        jsonResponse.put("fullName", "Test Repository");
        jsonResponse.put("description", "This is a test repository");

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/test/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponse.toString())));

        //Act
        GitHubResponseDTO response = gitHubClient.getRepository("test", "test");

        //Assert
        assertThat(response).isNotNull();

    }
}
