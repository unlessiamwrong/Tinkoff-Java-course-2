package edu.java.bot.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GitHubClient {
    @GetExchange("/repos/{owner}/{repo}")
    String getRepository(@PathVariable String owner, @PathVariable String repo);

}
