package edu.java.repositories.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

    @Query("select ul from User u join u.userLinks ul where u.id = :userId")
    List<Link> findAllUserLinksByUserId(@Param("userId") Long userId);
}
