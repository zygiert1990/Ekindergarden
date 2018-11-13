package ekindergarten.repositories.childProgress;

import ekindergarten.domain.childProgress.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findAllByChild_Id(long id);

}
