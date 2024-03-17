package edu.java.repositories.jooq;

import edu.java.domain.jdbc.Link;
import edu.java.domain.jdbc.User;
import edu.java.utilities.links.DataSet;
import edu.java.utilities.links.GetLinkDataItems;
import edu.java.utilities.links.GetLinkDataRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.domain.jooq.tables.Links.LINKS;
import static edu.java.domain.jooq.tables.UserLinks.USER_LINKS;

@SuppressWarnings("LineLength")
@RequiredArgsConstructor
@Component
public class JooqLinkRepository {

    private static final int INTERVAL_FOR_CHECK = 5;
    private final DSLContext create;

    private final JooqUserRepository jooqUserRepository;

    private final GetLinkDataItems getLinkDataItems;

    private final GetLinkDataRepository getLinkDataRepository;

    public Link add(long userId, URI url) {
        User user = jooqUserRepository.getUser(userId);
        Link link = null;
        if (user != null) {
            String urlString = url.toString();
            DataSet dataSet = getLinkDataItems.execute(urlString);
            OffsetDateTime lastUpdateAt;
            if (dataSet != null) {
                lastUpdateAt = dataSet.dateTime();
            } else {
                lastUpdateAt = getLinkDataRepository.execute(urlString);
            }
            create.insertInto(LINKS, LINKS.NAME, LINKS.LAST_UPDATE).values(urlString, lastUpdateAt).execute();
            Long linkId = create.selectFrom(LINKS).where(LINKS.NAME.eq(urlString)).fetchOne().getId();
            if (linkId != null) {
                link = new Link(linkId, urlString, lastUpdateAt);
                create.insertInto(USER_LINKS).set(USER_LINKS.USER_ID, userId).set(USER_LINKS.LINK_ID, linkId).execute();
            }
        }
        return link;
    }

    public Link remove(long userId, Link link) {
        Long linkId = link.getId();

        create.deleteFrom(USER_LINKS).where(USER_LINKS.USER_ID.eq(userId), USER_LINKS.LINK_ID.eq(linkId)).execute();
        List<Long> possibleUserLinkRelation = create.selectFrom(USER_LINKS).where(USER_LINKS.LINK_ID.eq(linkId)).fetch()
            .map(r -> r.get(USER_LINKS.LINK_ID));
        if (possibleUserLinkRelation.isEmpty()) {
            create.deleteFrom(LINKS).where(LINKS.ID.eq(linkId)).execute();
        }
        return link;

    }

    public List<Link> findAllUserLinks(long userId) {
        return create.select(LINKS.ID, LINKS.NAME)
            .from(LINKS)
            .join(USER_LINKS).on(LINKS.ID.eq(USER_LINKS.LINK_ID))
            .where(USER_LINKS.USER_ID.eq(userId))
            .fetch()
            .map(r -> new Link(r.get(LINKS.ID), r.get(LINKS.NAME)));
    }

    public Link getLinkFromUser(long userId, URI url) {
        String urlString = url.toString();
        List<Long> listLinkIdFromLinks =
            create.selectFrom(LINKS).where(LINKS.NAME.eq(urlString)).fetch().map(r -> r.get(LINKS.ID));
        if (listLinkIdFromLinks.isEmpty()) {
            return null;
        }
        List<Long> listLinkIdFromUserLinks = create.select(USER_LINKS.LINK_ID).from(USER_LINKS)
            .where(USER_LINKS.USER_ID.eq(userId), USER_LINKS.LINK_ID.eq(listLinkIdFromLinks.getFirst())).fetch()
            .map(r -> r.get(USER_LINKS.LINK_ID));
        if (listLinkIdFromUserLinks.isEmpty()) {
            return null;
        }
        return new Link(listLinkIdFromUserLinks.getFirst(), urlString);
    }

    public List<Link> findAll() {
        return create.select(LINKS.ID, LINKS.NAME, LINKS.LAST_UPDATE, LINKS.LAST_CHECK_FOR_UPDATE).from(LINKS)
            .where(LINKS.LAST_CHECK_FOR_UPDATE.isNull())
            .or(LINKS.LAST_CHECK_FOR_UPDATE.lessThan(OffsetDateTime.now().minusMinutes(INTERVAL_FOR_CHECK))).fetch().map(
                r -> new Link(
                    r.get(LINKS.ID),
                    r.get(LINKS.NAME),
                    r.get(LINKS.LAST_UPDATE),
                    r.get(LINKS.LAST_CHECK_FOR_UPDATE) != null
                        ? r.get(LINKS.LAST_CHECK_FOR_UPDATE)
                        : OffsetDateTime.now()
                )
            );

    }

    public List<Long> updateLinkGetRelatedUsers(
        long linkId,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheckForUpdate
    ) {
        create.update(LINKS).set(LINKS.LAST_UPDATE, lastUpdate).set(LINKS.LAST_CHECK_FOR_UPDATE, lastCheckForUpdate)
            .where(LINKS.ID.eq(linkId)).execute();
        return create.selectFrom(USER_LINKS).where(USER_LINKS.LINK_ID.eq(linkId)).fetch()
            .map(r -> r.get(USER_LINKS.USER_ID));

    }

    public void updateLinkWithLastCheckForUpdate(
        long linkId,
        OffsetDateTime lastCheckForUpdate
    ) {
        create.update(LINKS).set(LINKS.LAST_CHECK_FOR_UPDATE, lastCheckForUpdate).where(LINKS.ID.eq(linkId)).execute();
    }
}
