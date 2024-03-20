package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import edu.java.exceptions.NotFoundException;
import edu.java.exceptions.UserAlreadyRegisteredException;
import edu.java.repositories.jpa.JpaLinkRepository;
import edu.java.repositories.jpa.JpaUserRepository;
import edu.java.services.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class JpaUserService implements UserService {

    private final JpaUserRepository jpaUserRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public void add(long userId) {
        if (jpaUserRepository.findById(userId).orElse(null) != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + userId + "' is already registered");
        }
        User user = new User();
        user.setId(userId);
        jpaUserRepository.save(user);
    }

    @Override
    public void remove(long userId) {
        User user = jpaUserRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new NotFoundException("User with id:'" + userId + "' is not found");
        }

        List<Link> allUserLinks = jpaUserRepository.findAllUserLinksByUserId(userId);
        List<Link> linksToDelete = new ArrayList<>(allUserLinks.size());
        for (Link link : allUserLinks) {
            List<Long> possibleOtherUserLinkRelation =
                jpaLinkRepository.findLinkIdsByLinkIdFromOtherUsers(userId, link.getId());
            if (possibleOtherUserLinkRelation.isEmpty()) {
                linksToDelete.add(link);
            }
        }
        jpaUserRepository.delete(user);
        jpaLinkRepository.deleteAll(linksToDelete);
    }

}
