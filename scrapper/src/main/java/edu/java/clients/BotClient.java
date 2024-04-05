package edu.java.clients;

import edu.java.dto.requests.LinkUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.PostExchange;

public interface BotClient {

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @PostExchange(value = "/updates", accept = MediaType.APPLICATION_JSON_VALUE)
    void getUpdates(@RequestBody @Valid List<LinkUpdateRequest> linkUpdateRequests);
}
