package edu.java.bot.models;

public class Link {

    public final String url;

    public GitHubResponseDTO gitHubInfo;
    public StackOfResponseDTO stackOfInfo;

    public Link(String url, GitHubResponseDTO json) {
        this.url = url;
        this.gitHubInfo = json;

    }

    public Link(String url, StackOfResponseDTO json) {
        this.url = url;
        this.stackOfInfo = json;

    }
}
