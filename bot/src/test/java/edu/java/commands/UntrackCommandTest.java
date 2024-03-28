//package edu.java.commands;
//
//import com.github.tomakehurst.wiremock.client.WireMock;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
//import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//class UntrackCommandTest extends AbstractIntegrationCommandsTest {
//    @Test
//    void whenUseUntrackCommand_AndUserNotRegistered_ReturnCorrectExceptionMessage() {
//        //Arrange
//        when(message.chat()).thenReturn(chat);
//        when(message.chat().id()).thenReturn(1L);
//        when(message.text()).thenReturn("stub");
//        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
//            .put("exceptionMessage", "User is not registered");
//        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo("/links?userId=1"))
//            .willReturn(aResponse()
//                .withStatus(409)
//                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .withBody(jsonResponseAsObject.toString())));
//
//        //Act
//        String response = untrackCommand.execute(message);
//
//        //Assert
//        assertThat(response).isEqualTo("User is not registered");
//
//    }
//
//    @Test
//    void whenUseUntrackCommand_AndUserRegistered_AndLinkDoesntExist_ReturnCorrectExceptionMessage() {
//        //Arrange
//        when(message.chat()).thenReturn(chat);
//        when(message.chat().id()).thenReturn(1L);
//        when(message.text()).thenReturn("stub");
//        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
//            .put("exceptionMessage", "Link does not exist");
//        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo("/links?userId=1"))
//            .willReturn(aResponse()
//                .withStatus(404)
//                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .withBody(jsonResponseAsObject.toString())));
//
//        //Act
//        String response = untrackCommand.execute(message);
//
//        //Assert
//        assertThat(response).isEqualTo("Link does not exist");
//
//    }
//
//    @Test
//    void whenUseUntrackCommand_AndUserRegistered_AndLinkExists_ReturnCorrectResponse() {
//        //Arrange
//        when(message.chat()).thenReturn(chat);
//        when(message.chat().id()).thenReturn(1L);
//        when(message.text()).thenReturn("stub");
//        ObjectNode jsonResponseAsObject = JsonNodeFactory.instance.objectNode()
//            .put("stub", "stub");
//        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo("/links?userId=1"))
//            .willReturn(aResponse()
//                .withStatus(200)
//                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .withBody(jsonResponseAsObject.toString())));
//
//        //Act
//        String response = untrackCommand.execute(message);
//
//        //Assert
//        assertThat(response).isEqualTo("Link untracked successfully");
//
//    }
//
//}
