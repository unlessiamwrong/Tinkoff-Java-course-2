package edu.java.clients;

import edu.java.dto.responses.github.commits.GitHubCommitResponse;
import edu.java.dto.responses.github.repository.GitHubRepositoryResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {

    String BASE_URL = "/repos/{owner}/{repo}";

    @GetExchange(value = BASE_URL, accept = MediaType.APPLICATION_JSON_VALUE)
    GitHubRepositoryResponse getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetExchange(value = BASE_URL + "/commits", accept = MediaType.APPLICATION_JSON_VALUE)
    List<GitHubCommitResponse> getCommits(@PathVariable String owner, @PathVariable String repo);

}
