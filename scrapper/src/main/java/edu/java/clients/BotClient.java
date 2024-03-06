package edu.java.clients;

import edu.java.dto.requests.LinkUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;

public interface BotClient {
    @GetExchange(value = "/updates", accept = MediaType.APPLICATION_JSON_VALUE)
    void getUpdates(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest);
}
