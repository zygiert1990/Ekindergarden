package ekindergarten.repositories;

import ekindergarten.domain.User;
import ekindergarten.domain.consultation.Consultation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    List<Consultation> findByDayAfter(LocalDate day);

    void deleteByIdAndTeacher(long id, User teacherId);



}
