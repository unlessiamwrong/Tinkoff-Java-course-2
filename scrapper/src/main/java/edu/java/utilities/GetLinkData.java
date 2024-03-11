package edu.java.utilities;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.utilities.parser.GitHubLinkParser;
import edu.java.utilities.parser.StackOfLinkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class GetLinkData {

    private final GitHubLinkParser gitHubLinkParser;

    private final StackOfLinkParser stackOfLinkParser;

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    @Autowired
    GetLinkData(GitHubLinkParser gitHubLinkParser, StackOfLinkParser stackOfLinkParser, GitHubClient gitHubClient, StackOfClient stackOfClient){

        this.gitHubLinkParser = gitHubLinkParser;
        this.stackOfLinkParser = stackOfLinkParser;
        this.gitHubClient = gitHubClient;
        this.stackOfClient = stackOfClient;
    }

    public OffsetDateTime execute(String linkUrl) {
        if (linkUrl.contains("github.com")) {
            String[] gitHubData = gitHubLinkParser.parse(linkUrl);
            return gitHubClient.getRepository(gitHubData[0], gitHubData[1]).updatedAt();
        }
        if (linkUrl.contains("stackoverflow.com")) {
            System.out.println(1);
            String questionId = stackOfLinkParser.parse(linkUrl);
            System.out.println(questionId);
            long lastActivityDate =  stackOfClient.getQuestion(questionId, "stackoverflow").getItems().getFirst().getLastActivityDate();
            Instant instant = Instant.ofEpochSecond(lastActivityDate);
            return instant.atOffset(ZoneOffset.UTC);
        }
        return null;

    }
}
