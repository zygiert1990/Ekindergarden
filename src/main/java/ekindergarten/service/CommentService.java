package ekindergarten.service;

import ekindergarten.domain.forum.Comment;
import ekindergarten.domain.forum.Topic;
import ekindergarten.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findByTopic(long topicId) {
        return commentRepository.findByTopic(Topic.builder().id(topicId).build());
    }

    public void save() {

    }
}
