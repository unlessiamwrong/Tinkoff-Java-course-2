package edu.java.bot.repositories;

import edu.java.bot.models.responses.GitHubRepositoryResponse;
import edu.java.bot.models.responses.StackOfQuestionResponse;

public class Link {

    public final String url;

    public GitHubRepositoryResponse gitHubInfo;
    public StackOfQuestionResponse stackOfInfo;

    public Link(String url, GitHubRepositoryResponse json) {
        this.url = url;
        this.gitHubInfo = json;

    }

    public Link(String url, StackOfQuestionResponse json) {
        this.url = url;
        this.stackOfInfo = json;

    }
}
