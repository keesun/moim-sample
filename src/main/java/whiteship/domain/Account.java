package whiteship.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * @author Keeun Baik
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private boolean admin;

    @OneToMany(mappedBy = "owner")
    private List<Event> events;

}
