package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    @Query("select l.id from User u join u.userLinks l where u.id <> :userId and l.id = :linkId")
    List<Long> findLinkIdsByUserIdAndLinkId(@Param("userId") Long userId, @Param("linkId") Long linkId);
}
