package ekindergarten.repositories;

import ekindergarten.domain.forum.Comment;
import ekindergarten.domain.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTopic(Topic topic);

}
