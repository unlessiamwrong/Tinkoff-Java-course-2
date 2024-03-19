package edu.java.domain.jpa;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(name = "user_links", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "link_id"))
    private List<Link> userLinks;

}
