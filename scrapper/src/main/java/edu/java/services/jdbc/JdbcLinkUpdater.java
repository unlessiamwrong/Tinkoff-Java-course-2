package edu.java.services.jdbc;

import edu.java.domain.Link;
import edu.java.repositories.LinkRepository;
import edu.java.services.LinkUpdater;
import edu.java.utilities.GetLinkData;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkRepository linkRepository;

    private final GetLinkData getLinkData;

    @Autowired JdbcLinkUpdater(LinkRepository linkRepository, GetLinkData getLinkData) {

        this.linkRepository = linkRepository;
        this.getLinkData = getLinkData;

    }

    @Override
    public List<Link> update() {
        return linkRepository.findAll();
    }


}
