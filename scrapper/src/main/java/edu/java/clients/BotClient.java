package edu.java.clients;

import edu.java.dto.requests.LinkUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import java.util.List;

public interface BotClient {
    @PostExchange(value = "/updates", accept = MediaType.APPLICATION_JSON_VALUE)
    void getUpdates(@RequestBody @Valid List<LinkUpdateRequest> linkUpdateRequests);
}
