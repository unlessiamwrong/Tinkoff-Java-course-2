package edu.java.services.jdbc;

import edu.java.domain.Link;
import edu.java.domain.User;
import edu.java.exceptions.InvalidParamsException;
import edu.java.exceptions.NotFoundException;
import edu.java.repositories.LinkRepository;
import edu.java.repositories.UserRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.utilities.links.LinkChecker.isLinkValid;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    @Autowired JdbcLinkService(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Link add(long userId, URI url) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:" + userId + " is not found");
        }
        if (!isLinkValid(url)) {
            throw new InvalidParamsException("Link:" + url + " is invalid. Use Github's or StackOverflow's links");
        }
        return linkRepository.add(userId, url);
    }

    @Override
    public Link remove(long userId, URI url) {
        User user = userRepository.getUser(userId);

        if (user == null) {
            throw new NotFoundException("User with id:" + userId + " is not found");
        }

        Link link = linkRepository.getLinkFromUser(userId, url);
        if (link == null) {
            throw new NotFoundException("Link:" + url + " does not exist");
        } else {
            linkRepository.remove(userId, link);
        }
        return link;
    }

    @Override
    public List<Link> listAll(long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("User with id:" + userId + " is not found");
        }
        return linkRepository.findAllUserLinks(userId);
    }
}
