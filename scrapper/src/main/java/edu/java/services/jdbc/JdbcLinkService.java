package edu.java.services.jdbc;

import edu.java.domain.Link;
import edu.java.repositories.LinkRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Autowired
    JdbcLinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link add(long userId, URI url) {
        return linkRepository.add(userId, url);

    }

    @Override
    public Link remove(long userId, URI url) {
        return linkRepository.remove(userId, url);
    }

    @Override
    public List<Link> listAll(long userId) {
        return linkRepository.findAll(userId);
    }
}
