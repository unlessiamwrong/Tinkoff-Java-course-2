package edu.java.clients;

import edu.java.dto.responses.github.commits.GitHubCommitResponse;
import edu.java.dto.responses.github.repository.GitHubRepositoryResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {

    String BASE_URL = "/repos/{owner}/{repo}";

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @GetExchange(value = BASE_URL, accept = MediaType.APPLICATION_JSON_VALUE)
    GitHubRepositoryResponse getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo);

    @Retryable(retryFor = WebClientException.class,
               maxAttemptsExpression = "${app.retry-max-attempts}",
               backoff = @Backoff(delayExpression = "${app.retry-delay}", random = true))
    @GetExchange(value = BASE_URL + "/commits", accept = MediaType.APPLICATION_JSON_VALUE)
    List<GitHubCommitResponse> getCommits(@PathVariable("owner") String owner, @PathVariable("repo") String repo);

}
