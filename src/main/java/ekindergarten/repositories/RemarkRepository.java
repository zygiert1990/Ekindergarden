package ekindergarten.repositories;

import ekindergarten.domain.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
}
