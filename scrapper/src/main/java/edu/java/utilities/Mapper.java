package edu.java.utilities;

import edu.java.domain.jdbc.Link;
import edu.java.dto.responses.LinkResponse;
import edu.java.dto.responses.ListLinksResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mapper {

    public static LinkResponse executeForObject(Link link) {
        return new LinkResponse(link.getId(), URI.create(link.getName()));
    }

    public static ListLinksResponse executeForList(List<Link> links) {
        List<LinkResponse> linkResponses = new ArrayList<>(links.size());
        for (Link link : links) {
            linkResponses.add(new LinkResponse(link.getId(), URI.create(link.getName())));
        }
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }
}
