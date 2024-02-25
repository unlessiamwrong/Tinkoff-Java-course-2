package edu.java.bot.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOfClient {
    @GetExchange("/questions/{id}")
    String getQuestion(@PathVariable String id, @RequestParam("site") String site);

}
