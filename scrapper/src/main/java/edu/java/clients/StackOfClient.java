package edu.java.clients;

import edu.java.dto.responses.stackoverflow.answers.StackOfAnswersResponse;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionResponse;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOfClient {

    String BASE_URL = "/questions/{id}";

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @GetExchange(value = BASE_URL, accept = MediaType.APPLICATION_JSON_VALUE)
    StackOfQuestionResponse getQuestion(@PathVariable("id") String id, @RequestParam("site") String site);

    /**
     * Get all answers of the question
     *
     * @param order The order of answers ("asc", "desc")
     * @param sort  The sorting of answers ("creation", "activity", "votes")
     * @param site  The website name (e.g. "stackoverflow")
     */
    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @GetExchange(value = BASE_URL + "/answers", accept = MediaType.APPLICATION_JSON_VALUE)
    StackOfAnswersResponse getAnswers(
        @PathVariable("id") String id,
        @RequestParam("order") String order,
        @RequestParam("sort") String sort,
        @RequestParam("site") String site
    );
}
