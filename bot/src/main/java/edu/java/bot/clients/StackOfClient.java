package edu.java.bot.clients;

import edu.java.bot.models.StackOfResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOfClient {
    @GetExchange(value = "/questions/{id}", accept = MediaType.APPLICATION_JSON_VALUE)
    StackOfResponseDTO getQuestion(@PathVariable String id, @RequestParam("site") String site);

}
