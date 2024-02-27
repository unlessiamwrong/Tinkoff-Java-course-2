package edu.java.bot.commands.commandmanagers.trackcommandmanager;

import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOfClient;
import edu.java.bot.models.Link;
import edu.java.bot.utilities.parser.GitHubLinkParser;
import edu.java.bot.utilities.parser.StackOfLinkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackLinkParser {

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    @Autowired
    public TrackLinkParser(GitHubClient gitHubClient, StackOfClient stackOfClient) {
        this.gitHubClient = gitHubClient;
        this.stackOfClient = stackOfClient;
    }

    public Link execute(String linkUrl) {
        if (linkUrl.contains("github.com")) {
            GitHubLinkParser gitHubParser = new GitHubLinkParser();
            String[] gitHubData = gitHubParser.parse(linkUrl);
            String json = gitHubClient.getRepository(gitHubData[0], gitHubData[1]);
            return new Link(linkUrl, json);
        }
        if (linkUrl.contains("stackoverflow.com")) {
            StackOfLinkParser stackParser = new StackOfLinkParser();
            String questionId = stackParser.parse(linkUrl);
            String json = stackOfClient.getQuestion(questionId, "stackoverflow");
            return new Link(linkUrl, json);
        }
        return null;

    }
}
