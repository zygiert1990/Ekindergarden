package ekindergarten.repositories;

import ekindergarten.domain.consultation.ConsultationHours;
import ekindergarten.model.consultation.dto.ConsultationsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConsultationHoursRepository extends JpaRepository<ConsultationHours, Long> {

    @Transactional
    @Modifying
    @Query(value = "update TECZA_DB.consultation_hours set child_id = ?1 where consultation_id = ?2 and hour = ?3 and min = ?4", nativeQuery = true)
    void bookConsultation(Long childId, Long consultation_id, int hour ,int min);

    List<ConsultationsDto> findByChildId(long childId);
}
