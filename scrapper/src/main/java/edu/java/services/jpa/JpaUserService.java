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
@SuppressWarnings("MultipleStringLiterals")
public class JpaUserService implements UserService {

    private final JpaUserRepository jpaUserRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public void add(long chatId) {
        if (jpaUserRepository.findByChatId(chatId) != null) {
            throw new UserAlreadyRegisteredException("User with id:'" + chatId + "' is already registered");
        }
        User user = new User();
        user.setChatId(chatId);
        jpaUserRepository.save(user);
    }

    @Override
    public void remove(long chatId) {
        User user = jpaUserRepository.findByChatId(chatId);
        if (user == null) {
            throw new NotFoundException("User with id:'" + chatId + "' is not found");
        }

        Long userId = user.getId();
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
