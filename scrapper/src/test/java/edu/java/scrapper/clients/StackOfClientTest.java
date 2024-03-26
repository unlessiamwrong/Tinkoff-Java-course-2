package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.responses.stackoverflow.answers.StackOfAnswersResponse;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionResponse;
import edu.java.scrapper.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class StackOfClientTest extends AbstractIntegrationTest {

    @Test
    public void when_UseGETQuestion_ToStackOfQuestion_ReturnMockBody() {
        //Arrange
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/questions/test?site=stackoverflow"))
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
    public void when_UseGETAnswers_ToStackOfQuestion_ReturnMockBody() {
        //Arrange
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(
                "/questions/test/answers?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(jsonResponseAsObject.toString())));

        //Act

        StackOfAnswersResponse response = stackOfClient.getAnswers("test", "desc", "activity", "stackoverflow");

        //Assert
        assertThat(response).isNotNull();
    }
}
