package edu.java.bot.clients;

import edu.java.bot.models.responses.GitHubRepositoryResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {
    @GetExchange(value = "/repos/{owner}/{repo}", accept = MediaType.APPLICATION_JSON_VALUE)
    GitHubRepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repo);

}
