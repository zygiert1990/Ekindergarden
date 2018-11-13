package ekindergarten.repositories.childProgress;

import ekindergarten.domain.childProgress.ProgressTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressTaskRepository extends JpaRepository<ProgressTask, Long> {
}
