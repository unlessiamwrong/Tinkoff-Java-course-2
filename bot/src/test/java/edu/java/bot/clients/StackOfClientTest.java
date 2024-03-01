package edu.java.bot.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.bot.AbstractIntegrationTest;
import edu.java.bot.models.responses.StackOfQuestionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class StackOfClientTest extends AbstractIntegrationTest {

    @Test
    public void when_UseGET_ToStackOfQuestion_ReturnMockBody() {
        //Arrange

        ObjectNode jsonResponse = JsonNodeFactory.instance.objectNode();
        jsonResponse.put("fullName", "Test Repository");
        jsonResponse.put("description", "This is a test repository");

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/questions/test?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponse.toString())));

        //Act
        StackOfQuestionResponse response = stackOfClient.getQuestion("test", "stackoverflow");

        //Assert
        assertThat(response).isNotNull();

    }
}
