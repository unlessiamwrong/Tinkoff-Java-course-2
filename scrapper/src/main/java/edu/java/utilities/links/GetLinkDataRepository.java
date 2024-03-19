package edu.java.utilities.links;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOfClient;
import edu.java.dto.responses.github.repository.GitHubRepositoryResponse;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionResponse;
import edu.java.utilities.parser.GitHubLinkParser;
import edu.java.utilities.parser.StackOfLinkParser;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetLinkDataRepository {

    private final GitHubLinkParser gitHubLinkParser;

    private final StackOfLinkParser stackOfLinkParser;

    private final GitHubClient gitHubClient;

    private final StackOfClient stackOfClient;

    public OffsetDateTime execute(String linkUrl) {
        if (linkUrl.contains("github.com")) {
            String[] gitHubData = gitHubLinkParser.parse(linkUrl);
            GitHubRepositoryResponse gitHubRepositoryResponse =
                gitHubClient.getRepository(gitHubData[0], gitHubData[1]);
            return gitHubRepositoryResponse.updatedAt();

        }
        if (linkUrl.contains("stackoverflow.com")) {
            String questionId = stackOfLinkParser.parse(linkUrl);
            StackOfQuestionResponse stackOfQuestionResponse = stackOfClient.getQuestion(questionId, "stackoverflow");

            long date = stackOfQuestionResponse.items().getFirst().lastActivityDate();
            Instant instant = Instant.ofEpochSecond(date);

            return instant.atOffset(ZoneOffset.UTC);

        }
        return null;
    }
}
