package whiteship.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Keeun Baik
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String descr;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @ManyToOne
    private Account owner;

}
