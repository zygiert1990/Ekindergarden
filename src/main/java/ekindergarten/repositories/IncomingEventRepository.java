package ekindergarten.repositories;

import ekindergarten.domain.Child;
import ekindergarten.domain.IncomingEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomingEventRepository extends JpaRepository<IncomingEvents, Long> {

    List<IncomingEvents> findByChild(Child child);
}
