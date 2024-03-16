package edu.java.utilities.links;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.dto.responses.github.commits.GitHubCommitResponse;
import edu.java.dto.responses.stackoverflow.answers.StackOfAnswersResponse;
import edu.java.utilities.parser.GitHubLinkParser;
import edu.java.utilities.parser.StackOfLinkParser;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings("ReturnCount")
@Component
public class GetLinkDataItems {

    private final GitHubLinkParser gitHubLinkParser;

    private final StackOfLinkParser stackOfLinkParser;

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    @Autowired GetLinkDataItems(
        GitHubLinkParser gitHubLinkParser,
        StackOfLinkParser stackOfLinkParser,
        GitHubClient gitHubClient,
        StackOfClient stackOfClient
    ) {

        this.gitHubLinkParser = gitHubLinkParser;
        this.stackOfLinkParser = stackOfLinkParser;
        this.gitHubClient = gitHubClient;
        this.stackOfClient = stackOfClient;
    }

    public DataSet execute(String linkUrl) {
        if (linkUrl.contains("github.com")) {
            String[] gitHubData = gitHubLinkParser.parse(linkUrl);
            List<GitHubCommitResponse> gitHubCommitResponseList =
                gitHubClient.getCommits(gitHubData[0], gitHubData[1]);
            if (gitHubCommitResponseList.isEmpty()) {
                return null;
            }
            GitHubCommitResponse gitHubCommitResponse = gitHubCommitResponseList.getFirst();
            String author = gitHubCommitResponse.gitHubCommit().gitHubAuthor().name();
            OffsetDateTime date = gitHubCommitResponse.gitHubCommit().gitHubAuthor().date();

            return new DataSet(date, author, "commit");
        }
        if (linkUrl.contains("stackoverflow.com")) {
            String questionId = stackOfLinkParser.parse(linkUrl);
            StackOfAnswersResponse stackOfAnswersResponse =
                stackOfClient.getAnswers(questionId, "desc", "creation", "stackoverflow");
            if (stackOfAnswersResponse.items().isEmpty()) {
                return null;
            }
            String author = stackOfAnswersResponse.items().getFirst().owner().name();
            long date = stackOfAnswersResponse.items().getFirst().creationDate();
            Instant instant = Instant.ofEpochSecond(date);

            return new DataSet(instant.atOffset(ZoneOffset.UTC), author, "answer");

        }
        return null;
    }
}
