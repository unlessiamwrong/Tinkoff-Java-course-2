package edu.java.commands;

import edu.java.clients.ScrapperClient;
import edu.java.dto.responses.LinkResponse;
import edu.java.utilities.others.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Order(5)
public class ListCommand implements TelegramBotCommand {

    private final ScrapperClient scrapperClient;

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "Show all tracked links";
    }

    public String execute(Long userId) {
        StringBuilder response = new StringBuilder("Your current tracked links: \n");
        List<LinkResponse> links;
        try {
            links = scrapperClient.getLinks(userId).links();
            for (int i = 0; i < links.size(); i++) {
                response.append((i + 1))
                    .append(". ")
                    .append(links.get(i).url())
                    .append("\n");

            }

        } catch (WebClientResponseException e) {
            return Mapper.getExceptionMessage(e.getResponseBodyAsString());
        }
        return response.toString();
    }
}

