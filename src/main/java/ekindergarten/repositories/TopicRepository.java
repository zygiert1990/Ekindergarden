package ekindergarten.repositories;

import ekindergarten.domain.User;
import ekindergarten.domain.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Transactional
    @Modifying
    @Query ("delete from Topic where id=?1 and author = ?2")
    int deleteByIdAndAuthor(long id, User author);

}
