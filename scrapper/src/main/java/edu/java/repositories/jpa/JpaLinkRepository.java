package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    Link findByNameLike(String urlString);

    @Query("SELECT l.id FROM User u JOIN u.userLinks l WHERE u.id <> :userId AND l.id = :linkId")
    List<Long> findLinkIdsByLinkIdFromOtherUsers(
        @Param("userId") Long userId,
        @Param("linkId") Long linkId
    );

    @Query("SELECT u FROM User u JOIN u.userLinks l WHERE l.id = :linkId")
    List<User> findUsersByLinkId(@Param("linkId") Long linkId);

    @Modifying
    @Transactional
    @Query("UPDATE Link l SET l.lastCheckForUpdate = :lastCheckForUpdate WHERE l.id = :linkId")
    void updateLinkWithLastCheckForUpdate(
        @Param("linkId") Long linkId,
        @Param("lastCheckForUpdate") OffsetDateTime lastCheckForUpdate
    );

    @Modifying
    @Transactional
    @Query(
        "UPDATE Link l SET l.lastUpdate = :lastUpdate, l.lastCheckForUpdate = :lastCheckForUpdate WHERE l.id = :linkId")
    void updateLinkWithLastUpdateAndLastCheckForUpdate(
        @Param("linkId") Long linkId,
        @Param("lastUpdate") OffsetDateTime lastUpdate,
        @Param("lastCheckForUpdate") OffsetDateTime lastCheckForUpdate
    );

}
