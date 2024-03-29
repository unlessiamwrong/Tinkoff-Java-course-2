package edu.java.kafka.commands;

import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.services.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand {

    private final LinkService linkService;

    public String execute(Long userId) {
        ListLinksResponse listLinksResponse = null;
        try {
            listLinksResponse = linkService.listAll(userId);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        List<LinkResponse> links = listLinksResponse.links();

        int linkSize = links.size();
        if (linkSize == 0) {
            return "List is empty. You can add links with command /track";
        }

        StringBuilder response = new StringBuilder("Your current tracked links: \n");
        for (int i = 0; i < linkSize; i++) {
            response.append((i + 1))
                .append(". ")
                .append(links.get(i).url())
                .append("\n");
        }
        return response.toString();
    }
}
