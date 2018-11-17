package ekindergarten.repositories;

import ekindergarten.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "select n from News n join n.groups g where g.id = ?1")
    List<News> findAllByGroupId(long id);

}
