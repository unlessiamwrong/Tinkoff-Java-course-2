package edu.java.bot.commands;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class StartCommandTest extends AbstractIntegrationCommandsTest {

    @Test
    void whenUseStartCommand_AndUserNotRegistered_ReturnCorrectResponse() {
        //Arrange
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/users/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody("stubBody")));

        //Act
        String response = startCommand.execute(1L);

        assertThat(response).isEqualTo("You are successfully registered");

    }

    @Test
    void whenUseStartCommand_AndUserRegistered_ReturnCorrectExceptionMessage() {
        //Arrange
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("exceptionMessage", "User is already registered");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/users/1"))
            .willReturn(aResponse()
                .withStatus(409)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = startCommand.execute(1L);

        assertThat(response).isEqualTo("User is already registered");

    }

}
