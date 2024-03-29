package edu.java.kafka.commands;

import edu.java.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class UntrackCommand {

    private final LinkService linkService;

    public String execute(Long userId, URI link) {
        try {
            linkService.remove(userId, link);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "Link untracked successfully";
    }
}
