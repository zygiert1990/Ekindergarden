package ekindergarten.repositories.childProgress;

import ekindergarten.domain.childProgress.ProgressCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressCategoryRepository extends JpaRepository<ProgressCategory, Long> {
}
