package edu.java.services.jdbc;

import edu.java.advice.InvalidParamsException;
import edu.java.advice.NotFoundException;
import edu.java.domain.Link;
import edu.java.domain.User;
import edu.java.repositories.LinkRepository;
import edu.java.repositories.UserRepository;
import edu.java.services.LinkService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static edu.java.utilities.LinkChecker.isLinkValid;

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
            throw new NotFoundException();
        }
        if(!isLinkValid(url)){
            throw new InvalidParamsException();
        }
        return linkRepository.add(userId, url);
    }



    @Override
    public Link remove(long userId, URI url) {
        User user = userRepository.getUser(userId);;
        if(user == null) {
            throw new NotFoundException();
        }
        else if(!isLinkValid(url)){
            throw new InvalidParamsException();
        }
        return linkRepository.remove(userId, url);
    }

    @Override
    public List<Link> listAll(long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        return linkRepository.findAll(userId);
    }
}
