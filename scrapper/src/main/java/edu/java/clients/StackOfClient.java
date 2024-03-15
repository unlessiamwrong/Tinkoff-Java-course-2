package edu.java.clients;

import edu.java.dto.responses.stackoverflow.answers.StackOfAnswersResponse;
import edu.java.dto.responses.stackoverflow.question.StackOfQuestionResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOfClient {

    String BASE_URL = "/questions/{id}";

    @GetExchange(value = BASE_URL, accept = MediaType.APPLICATION_JSON_VALUE)
    StackOfQuestionResponse getQuestion(@PathVariable String id, @RequestParam("site") String site);

    /**
     * Get all answers of the question
     *
     * @param order The order of answers ("asc", "desc")
     * @param sort  The sorting of answers ("creation", "activity", "votes")
     * @param site  The website name (e.g. "stackoverflow")
     */
    @GetExchange(value = BASE_URL + "/answers", accept = MediaType.APPLICATION_JSON_VALUE)
    StackOfAnswersResponse getAnswers(
        @PathVariable String id,
        @RequestParam("order") String order,
        @RequestParam("sort") String sort,
        @RequestParam("site") String site
    );
}
