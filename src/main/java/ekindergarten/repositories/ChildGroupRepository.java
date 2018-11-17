package ekindergarten.repositories;

import ekindergarten.domain.ChildGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildGroupRepository extends JpaRepository<ChildGroup, Long> {
}
