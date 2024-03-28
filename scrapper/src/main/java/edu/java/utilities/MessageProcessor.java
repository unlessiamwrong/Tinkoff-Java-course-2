package edu.java.utilities;

import edu.java.dto.kafka.Message;
import edu.java.dto.kafka.Response;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.services.LinkService;
import edu.java.services.UserService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final UserService userService;

    private final LinkService linkService;
    private final KafkaTemplate<String, Response> responseKafkaTemplate;

    public void analyzeAndExecute(Message message) {
        Long userId = message.id();
        String command = message.command();
        URI link = message.link();
        String response = null;

        switch (command) {
            case "start" -> {
                response = startCommand(userId);
            }
            case "track" -> {
                response = trackCommand(userId, link);
            }
            case "untrack" -> {
                response = untrackCommand(userId, link);
            }
            case "list" -> {
                response = listCommand(userId);
            }
        }
        responseKafkaTemplate.send("response", new Response(userId, response));

    }

    private String startCommand(Long userId) {
        try {
            userService.add(userId);
        } catch (UserAlreadyRegisteredException e) {
            return e.getMessage();
        }
        return "You are successfully registered";
    }

    private String trackCommand(Long userId, URI link) {
        try {
            linkService.add(userId, link);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "Link added successfully";
    }

    private String untrackCommand(Long userId, URI link) {
        try {
            linkService.remove(userId, link);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "Link untracked successfully";
    }

    private String listCommand(Long userId) {
        ListLinksResponse listLinksResponse = null;
        try {
            listLinksResponse = linkService.listAll(userId);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        List<LinkResponse> links = listLinksResponse.links();

        int linkSize = links.size();
        if(linkSize == 0){
            return "List is empty. You can add links with command /track";
        }

        StringBuilder response = new StringBuilder("Your current tracked links: \n");
        for(int i = 0; i < linkSize; i++){
            response.append((i + 1))
                .append(". ")
                .append(links.get(i).url())
                .append("\n");
        }
        return response.toString();
    }

}
