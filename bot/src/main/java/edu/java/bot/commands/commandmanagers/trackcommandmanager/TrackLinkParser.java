package edu.java.bot.commands.commandmanagers.trackcommandmanager;

import edu.java.bot.clients.GitHubClient;
import edu.java.bot.clients.StackOfClient;
import edu.java.bot.models.GitHubResponseDTO;
import edu.java.bot.models.Link;
import edu.java.bot.models.StackOfResponseDTO;
import edu.java.bot.utilities.parser.GitHubLinkParser;
import edu.java.bot.utilities.parser.StackOfLinkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackLinkParser {

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    private final GitHubLinkParser gitHubLinkParser;

    private final StackOfLinkParser stackOfLinkParser;

    @Autowired
    public TrackLinkParser(
        GitHubClient gitHubClient,
        StackOfClient stackOfClient,
        GitHubLinkParser gitHubLinkParser,
        StackOfLinkParser stackOfLinkParser
    ) {
        this.gitHubClient = gitHubClient;
        this.stackOfClient = stackOfClient;
        this.gitHubLinkParser = gitHubLinkParser;
        this.stackOfLinkParser = stackOfLinkParser;
    }

    public Link execute(String linkUrl) {
        if (linkUrl.contains("github.com")) {
            String[] gitHubData = gitHubLinkParser.parse(linkUrl);
            GitHubResponseDTO json = gitHubClient.getRepository(gitHubData[0], gitHubData[1]);
            return new Link(linkUrl, json);
        }
        if (linkUrl.contains("stackoverflow.com")) {
            String questionId = stackOfLinkParser.parse(linkUrl);
            StackOfResponseDTO json = stackOfClient.getQuestion(questionId, "stackoverflow");
            return new Link(linkUrl, json);
        }
        return null;

    }
}
