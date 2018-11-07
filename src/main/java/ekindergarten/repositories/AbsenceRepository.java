package ekindergarten.repositories;

import ekindergarten.domain.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Absence a WHERE a.id in (?1) and a.absenceDate >= ?2")
    int deleteById(List<Long> id, LocalDate currentDate);

    List<Absence> findByChildId(long child_id);
}
