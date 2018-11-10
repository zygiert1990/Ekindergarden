package ekindergarten.repositories;

import ekindergarten.domain.User;
import ekindergarten.domain.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Long deleteByIdAndAuthor(long id, User author);
}
