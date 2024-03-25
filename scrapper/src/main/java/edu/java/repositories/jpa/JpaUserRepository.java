package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    User findByChatId(Long userId);

    @Query("SELECT ul FROM User u JOIN u.userLinks ul WHERE u.id = :userId AND ul.id = :linkId")
    Link findUserLinkByUserIdAndLinkId(@Param("userId") Long userId, @Param("linkId") Long linkId);

    @EntityGraph(attributePaths = "userLinks")
    @Query("SELECT ul FROM User u JOIN u.userLinks ul WHERE u.id = :userId")
    List<Link> findAllUserLinksByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_links (user_id, link_id) VALUES (:userId, :linkId)", nativeQuery = true)
    void addUserLink(@Param("userId") Long userId, @Param("linkId") Long linkId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_links WHERE user_id = :userId AND link_id = :linkId", nativeQuery = true)
    void removeUserLink(@Param("userId") Long userId, @Param("linkId") Long linkId);
}

