package edu.java.repositories.jooq;

import edu.java.domain.jdbc.User;
import edu.java.domain.jooq.tables.records.UsersRecord;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.domain.jooq.tables.Links.LINKS;
import static edu.java.domain.jooq.tables.UserLinks.USER_LINKS;
import static edu.java.domain.jooq.tables.Users.USERS;

@RequiredArgsConstructor
public class JooqUserRepository {

    private final DSLContext create;

    public void add(UsersRecord user) {
        create.insertInto(USERS).set(USERS.ID, user.getId()).execute();
    }

    public void remove(UsersRecord user) {
        Long userId = user.getId();
        List<Long> linkIds =
            create.selectFrom(USER_LINKS).where(USER_LINKS.USER_ID.eq(userId))
                .fetch().map(r -> r.get(USER_LINKS.LINK_ID));
        for (Long linkId : linkIds) {
            List<Long> possibleUserLinkRelation =
                create.selectFrom(USER_LINKS).where(USER_LINKS.USER_ID.notEqual(userId))
                    .and(USER_LINKS.LINK_ID.eq(linkId)).fetch()
                    .map(r -> r.get(USER_LINKS.LINK_ID));
            if (possibleUserLinkRelation.isEmpty()) {
                create.deleteFrom(USER_LINKS).where(USER_LINKS.LINK_ID.eq(linkId)).execute();
                create.deleteFrom(LINKS).where(LINKS.ID.eq(linkId)).execute();
            } else {
                create.deleteFrom(USER_LINKS).where(USER_LINKS.USER_ID.eq(userId)).execute();
            }
        }
        create.deleteFrom(USERS).where(USERS.ID.eq(userId)).execute();
    }

    public List<User> findAll() {
        List<Long> userIds = create.selectFrom(USERS).fetch().map(r -> r.get(USERS.ID));
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            users.add(new User(userId));
        }
        return users;
    }

    public User getUser(long userId) {

        UsersRecord user = create.selectFrom(USERS).where(USERS.ID.eq(userId)).fetchOne();
        if (user == null) {
            return null;
        }
        return new User(user.getId());
    }
}
