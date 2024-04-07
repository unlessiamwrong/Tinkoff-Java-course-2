package edu.java.commands;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class TrackCommandTest extends AbstractIntegrationCommandsTest {
    @Test
    void whenUseTrackCommand_AndUserNotRegistered_ReturnCorrectExceptionMessage() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("stub");
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("exceptionMessage", "User is not registered");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(409)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = trackCommand.execute(message);

        //Assert
        assertThat(response).isEqualTo("User is not registered");

    }

    @Test
    void whenUseTrackCommand_AndUserRegistered_AndLinkIncorrect_ReturnCorrectExceptionMessage() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("stub");
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("exceptionMessage", "Link is incorrect");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = trackCommand.execute(message);

        //Assert
        assertThat(response).isEqualTo("Link is incorrect");

    }

    @Test
    void whenUseTrackCommand_AndUserRegistered_AndLinkCorrect_AndLinkExists_ReturnCorrectExceptionMessage() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("stub");
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("exceptionMessage", "Link already exists");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(409)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = trackCommand.execute(message);

        //Assert
        assertThat(response).isEqualTo("Link already exists");

    }

    @Test
    void whenUseTrackCommand_AndUserRegistered_AndLinkCorrect_ReturnCorrectResponse() {
        //Arrange
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(1L);
        when(message.text()).thenReturn("stub");
        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
            .put("stub", "stub");
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/links?userId=1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        String response = trackCommand.execute(message);

        //Assert
        assertThat(response).isEqualTo("Link added successfully");

    }
}
