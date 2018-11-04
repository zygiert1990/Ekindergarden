package ekindergarten.repositories;

import ekindergarten.domain.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {

    Remark findById(long id);

    List<Remark> findByChildId(Long child_id);
}
