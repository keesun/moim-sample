package whiteship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whiteship.domain.Event;

/**
 * @author Keeun Baik
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
