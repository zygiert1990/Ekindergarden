package ekindergarten.repositories.childProgress;

import ekindergarten.domain.childProgress.ProgressGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressGradeRepository extends JpaRepository<ProgressGrade, Long> {
}
