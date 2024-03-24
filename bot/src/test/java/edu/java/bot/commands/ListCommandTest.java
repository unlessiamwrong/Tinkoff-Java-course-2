package edu.java.bot.commands;

import java.util.List;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ArrayNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

class ListCommandTest extends AbstractIntegrationCommandsTest {

    @Test
    void whenUseListCommand_AndUserNotRegistered_ReturnCorrectExceptionMessage() {
        //Arrange
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("exceptionMessage", "User is not registered");
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(404)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = listCommand.execute(1L);

        //Assert
        assertThat(response).isEqualTo("User is not registered");

    }

    @Test
    void whenUseListCommand_AndUserRegistered_ReturnCorrectResponse() {
        //Arrange
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode();
        jsonResponseAsObject.set("links", JsonNodeFactory.instance.arrayNode());
        jsonResponseAsObject.put("size", 0);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = listCommand.execute(1L);

        //Assert
        assertThat(response).isEqualTo("Your current tracked links: \n");

    }
}
