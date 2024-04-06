package edu.java.scrapper.clients;

import edu.java.dto.responses.stackoverflow.answers.StackOfAnswersResponse;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionResponse;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientException;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StackOfClientTest extends AbstractIntegrationTest {

    @Test
    public void whenUse_GetQuestion_ReturnMockedBody() {
        //Arrange
        stubFor(get(urlEqualTo("/questions/test?site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        StackOfQuestionResponse response = stackOfClient.getQuestion("test", "stackoverflow");

        //Assert
        assertThat(response).isNotNull();
    }

    @Test
    public void whenUse_GetAnswers_ReturnMockedBody() {
        //Arrange
        stubFor(get(urlEqualTo(
            "/questions/test/answers?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act
        StackOfAnswersResponse response = stackOfClient
            .getAnswers("test", "desc", "activity", "stackoverflow");

        //Assert
        assertThat(response).isNotNull();
    }

    @Test
    public void whenUse_GetQuestion_AndServerReturnsException_RetryIsWorking() {
        //Arrange
        stubFor(get(urlEqualTo("/questions/test?site=stackoverflow"))
            .willReturn(aResponse().withStatus(500)));

        //Act
        assertThrows(WebClientException.class, () -> stackOfClient.getQuestion("test", "stackoverflow"));

        //Assert
        verify(stackOfClient, times(2)).getQuestion(anyString(), anyString());
    }

    @Test
    public void whenUse_GetAnswers_AndServerReturnsException_RetryIsWorking() {
        //Arrange
        stubFor(get(urlEqualTo(
            "/questions/test/answers?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse().withStatus(500)));
        //Act
        assertThrows(WebClientException.class, () -> stackOfClient
            .getAnswers("test", "desc", "activity", "stackoverflow"));

        //Assert
        verify(stackOfClient, times(2)).getAnswers(anyString(), anyString(), anyString(), anyString());
    }
}
