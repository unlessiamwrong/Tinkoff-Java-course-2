package edu.java.clients;

import edu.java.dto.requests.AddLinkRequest;
import edu.java.dto.requests.RemoveLinkRequest;
import edu.java.dto.responses.ListLinksResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @PostExchange("/users/{id}")
    void postUser(@PathVariable Long id);

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @DeleteExchange("/users/{id}")
    void deleteUser(@PathVariable Long id);

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @GetExchange(value = "/links")
    ListLinksResponse getLinks(@RequestParam("userId") long userId);

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @PostExchange(value = "/links", accept = MediaType.APPLICATION_JSON_VALUE)
    void postLink(@RequestParam("userId") long userId, @RequestBody @Valid AddLinkRequest addLinkRequest);

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @DeleteExchange(value = "/links", accept = MediaType.APPLICATION_JSON_VALUE)
    void deleteLink(@RequestParam("userId") long userId, @RequestBody @Valid RemoveLinkRequest removeLinkRequest);

}
