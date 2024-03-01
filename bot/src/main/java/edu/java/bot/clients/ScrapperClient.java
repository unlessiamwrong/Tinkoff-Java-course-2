package edu.java.bot.clients;

import edu.java.bot.models.requests.AddLinkRequest;
import edu.java.bot.models.requests.RemoveLinkRequest;
import edu.java.bot.models.responses.LinkResponse;
import edu.java.bot.models.responses.ListLinksResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {

    @PostExchange("/tg-chat/{id}")
    void postTgChat(@PathVariable Long id);

    @DeleteExchange("/tg-chat/{id}")
    void deleteTgChat(@PathVariable Long id);

    @GetExchange(value = "/links")
    ListLinksResponse getLinks(@RequestHeader long tgChatId);

    @PostExchange(value = "/links", accept = MediaType.APPLICATION_JSON_VALUE)
    LinkResponse postLink(@RequestHeader long tgChatId, @RequestBody AddLinkRequest addLinkRequest);

    @DeleteExchange(value = "/links", accept = MediaType.APPLICATION_JSON_VALUE)
    LinkResponse deleteLink(@RequestHeader long tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest);

}