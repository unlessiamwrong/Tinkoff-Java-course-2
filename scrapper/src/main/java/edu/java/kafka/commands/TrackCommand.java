package edu.java.kafka.commands;

import edu.java.services.LinkService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand {

    private final LinkService linkService;

    public String execute(Long userId, URI link) {
        try {
            linkService.add(userId, link);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "Link added successfully";
    }
}
