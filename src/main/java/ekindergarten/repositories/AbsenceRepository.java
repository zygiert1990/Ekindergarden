package ekindergarten.repositories;

import ekindergarten.domain.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    Absence findById(long id);

}
